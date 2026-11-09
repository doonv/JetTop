package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.Cached
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleRepository
import doonv.jettop.data.TokenExpiredException
import doonv.jettop.data.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardViewModel(
    app: Application,
    private val scheduleRepository: ScheduleRepository,
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.dataStore
    val state: StateFlow<UiState<Cached<List<DaySchedule>>>> field =
        MutableStateFlow<UiState<Cached<List<DaySchedule>>>>(UiState.Loading)

    init {
        runBlocking {
            val login = store.data.first().login
            val cached = scheduleRepository.cache.loadBlocking()
            if (login != null && cached != null) {
                state.value = UiState.Success(cached)
            }
        }
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collectLatest { login ->
                if (login == null) {
                    state.value = UiState.Loading
                } else {
                    fetchSchedule(login)
                }
            }
        }
    }

    private suspend fun fetchSchedule(login: LoginData) {
        state.value = state.value.startRefreshing()
        val result = state.value.load {
            scheduleRepository.refresh(login)
        }
        if (result is UiState.Error && result.error is TokenExpiredException) {
            state.value = UiState.Loading
            scheduleRepository.cache.clear()
            store.updateData { it.copy(login = null) }
        } else {
            state.value = result
        }
    }

    override fun refresh() {
        viewModelScope.launch {
            store.data.first().login?.let { fetchSchedule(it) }
        }
    }
}
