package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleFileCache
import doonv.jettop.data.ScheduleRepository
import doonv.jettop.data.TokenExpiredException
import doonv.jettop.data.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

sealed interface ScheduleUiState {
    fun refreshing(): Boolean = this is Success && this.isRefreshing

    data object Loading : ScheduleUiState
    data class Success(
        val days: List<DaySchedule>,
        val firstName: String,
        val lastUpdated: Long,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false
    ) : ScheduleUiState

    data class Error(val message: String) : ScheduleUiState
}

class ScheduleViewModel(
    app: Application,
    private val repository: ScheduleRepository
) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _state = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val state: StateFlow<ScheduleUiState> = _state

    constructor(app: Application) : this(
        app, ScheduleRepository(
            cache = ScheduleFileCache(
                file = app.applicationContext.filesDir.resolve(
                    "schedules.json"
                )
            )
        )
    )

    init {
        // Blocking here because fetching from data stores is extremely fast
        // but not blocking causes a flicker.
        runBlocking {
            val login = store.data.map { it.login }.first()
            val cached = repository.cache.loadBlocking()
            if (login != null && cached != null)
                _state.value =
                    ScheduleUiState.Success(cached.days, login.firstName, cached.updatedAt)
        }
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { login ->
                if (login != null) fetch(login)
            }
        }
    }

    private suspend fun fetch(login: LoginData) {
        (_state.value as? ScheduleUiState.Success)?.let {
            _state.value = it.copy(isRefreshing = true)
        }

        try {
            val fresh = repository.refresh(login)
            _state.value = ScheduleUiState.Success(fresh.days, login.firstName, fresh.updatedAt)
        } catch (e: TokenExpiredException) {
            repository.cache.clear()
            logout()
        } catch (e: IOException) {
            _state.value = when (val s = _state.value) {
                is ScheduleUiState.Success -> s.copy(isRefreshing = false, isOffline = true)
                else -> ScheduleUiState.Error("You're offline.")
            }
        } catch (e: Exception) {
            _state.value = ScheduleUiState.Error(e.localizedMessage ?: e.message ?: "Unknown error")
        }
    }

    fun refresh() {
        viewModelScope.launch {
            store.data.map { it.login }.first()?.let { fetch(it) }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.value = ScheduleUiState.Loading
            repository.cache.clear()
            store.updateData { it.copy(login = null) }
        }
    }
}

