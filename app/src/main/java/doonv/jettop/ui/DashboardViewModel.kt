package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.Cached
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleRepository
import doonv.jettop.data.TokenExpiredException
import doonv.jettop.data.loginDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardViewModel(
    app: Application,
    private val scheduleRepository: ScheduleRepository,
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.loginDataStore
    val state: StateFlow<UiState<Cached<List<DaySchedule>>>> field =
        MutableStateFlow<UiState<Cached<List<DaySchedule>>>>(UiState.Loading)

    init {
        runBlocking {
            val login = store.data.first()
            val cached = scheduleRepository.cache.loadBlocking()
            if (login != null && cached != null) {
                state.value = UiState.Success(cached)
            }
        }
        viewModelScope.launch {
            store.data.distinctUntilChanged().collectLatest { login ->
                if (login == null) {
                    state.value = UiState.Loading
                } else {
                    fetchSchedule(login, LoadTrigger.Automatic)
                }
            }
        }
    }

    private suspend fun fetchSchedule(login: LoginData, trigger: LoadTrigger) {
        state.value = state.value.startLoading(trigger)
        val result = state.value.load {
            scheduleRepository.refresh(login)
        }
        if (result is UiState.Error && result.error is TokenExpiredException) {
            state.value = UiState.Loading
            scheduleRepository.cache.clear()
            store.updateData { null }
        } else {
            state.value = result
        }
    }

    override fun refresh() {
        viewModelScope.launch {
            store.data.first()?.let { fetchSchedule(it, LoadTrigger.Refresh) }
        }
    }
}
