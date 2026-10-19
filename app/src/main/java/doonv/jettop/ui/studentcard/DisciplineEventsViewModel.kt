package doonv.jettop.ui.studentcard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.data.DisciplineEventsRepository
import doonv.jettop.data.LoginData
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

sealed interface DisciplineEventsUiState {
    fun refreshing(): Boolean = this is Success && this.isRefreshing

    data object Loading : DisciplineEventsUiState
    data class Success(
        val events: DisciplineEvents,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false
    ) : DisciplineEventsUiState

    data class Error(val message: String) : DisciplineEventsUiState
}

class DisciplineEventsViewModel(
    app: Application,
    private val repository: DisciplineEventsRepository
) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    val state: StateFlow<DisciplineEventsUiState> field =
        MutableStateFlow<DisciplineEventsUiState>(DisciplineEventsUiState.Loading)

    init {
        runBlocking {
            repository.loadBlocking()?.let { cached ->
                state.value = DisciplineEventsUiState.Success(cached.events)
            }
        }
        viewModelScope.launch {
            repository.events.collect { cached ->
                if (cached != null)
                    state.value = DisciplineEventsUiState.Success(cached.events)
            }
        }
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { login ->
                if (login != null) fetch(login)
                else {
                    repository.clear()
                    state.value = DisciplineEventsUiState.Loading
                }
            }
        }
    }

    private suspend fun fetch(login: LoginData) {
        (state.value as? DisciplineEventsUiState.Success)?.let {
            state.value = it.copy(isRefreshing = true)
        }
        try {
            val fresh = repository.refresh(login)
            state.value = DisciplineEventsUiState.Success(fresh.events)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            state.value = when (val s = state.value) {
                is DisciplineEventsUiState.Success -> s.copy(isRefreshing = false, isOffline = true)
                else -> DisciplineEventsUiState.Error(
                    e.localizedMessage ?: e.message
                    ?: getApplication<Application>().getString(R.string.unknown_error)
                )
            }
        } catch (e: Exception) {
            state.value = DisciplineEventsUiState.Error(
                e.localizedMessage ?: e.message
                ?: getApplication<Application>().getString(R.string.unknown_error)
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            store.data.map { it.login }.first()?.let { fetch(it) }
        }
    }
}
