package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.Cached
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.data.DisciplineEventsRepository
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleRepository
import doonv.jettop.data.TokenExpiredException
import doonv.jettop.data.dataStore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class DashboardData(
    val schedule: Cached<List<DaySchedule>>,
    val disciplineEvents: DisciplineEvents,
)

class DashboardViewModel(
    app: Application,
    private val scheduleRepository: ScheduleRepository,
    private val disciplineRepository: DisciplineEventsRepository,
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.dataStore

    private val scheduleState = MutableStateFlow<UiState<Cached<List<DaySchedule>>>>(UiState.Loading)
    private val disciplineState = MutableStateFlow<UiState<DisciplineEvents>>(UiState.Loading)
    private val refreshing = MutableStateFlow(false)

    init {
        runBlocking {
            val login = store.data.map { it.login }.first()
            val cachedSchedule = scheduleRepository.cache.loadBlocking()
            if (login != null && cachedSchedule != null)
                scheduleState.value = UiState.Success(cachedSchedule)
            disciplineRepository.loadBlocking()?.let { cached ->
                disciplineState.value = UiState.Success(cached.data)
            }
        }
        viewModelScope.launch {
            disciplineRepository.events.collect { cached ->
                if (cached != null)
                    disciplineState.value = UiState.Success(cached.data)
            }
        }
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { login ->
                if (login == null) {
                    disciplineRepository.clear()
                    disciplineState.value = UiState.Loading
                } else {
                    reload(login)
                }
            }
        }
    }

    val state: StateFlow<UiState<DashboardData>> =
        combine(scheduleState, disciplineState, refreshing) { schedule, discipline, isRefreshing ->
            reduce(schedule, discipline, isRefreshing)
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            reduce(scheduleState.value, disciplineState.value, refreshing.value)
        )

    private fun reduce(
        schedule: UiState<Cached<List<DaySchedule>>>,
        discipline: UiState<DisciplineEvents>,
        refreshing: Boolean,
    ): UiState<DashboardData> = when {
        schedule is UiState.Error -> schedule
        discipline is UiState.Error -> discipline
        schedule is UiState.Success && discipline is UiState.Success -> UiState.Success(
            data = DashboardData(
                schedule = schedule.data,
                disciplineEvents = discipline.data,
            ),
            isRefreshing = refreshing,
        )

        else -> UiState.Loading
    }

    private suspend fun fetchSchedule(login: LoginData) {
        val result = scheduleState.value.load {
            scheduleRepository.refresh(login)
        }
        if (result is UiState.Error && result.error is TokenExpiredException) {
            logout()
        } else {
            scheduleState.value = result
        }
    }

    private suspend fun fetchDiscipline(login: LoginData) {
        disciplineState.value = disciplineState.value.load {
            disciplineRepository.refresh(login).data
        }
    }

    override fun refresh() {
        viewModelScope.launch {
            store.data.map { it.login }.first()?.let { reload(it) }
        }
    }

    private suspend fun reload(login: LoginData) {
        refreshing.value = true
        try {
            coroutineScope {
                launch { fetchSchedule(login) }
                launch { fetchDiscipline(login) }
            }
        } finally {
            refreshing.value = false
        }
    }

    private fun logout() {
        viewModelScope.launch {
            scheduleState.value = UiState.Loading
            scheduleRepository.cache.clear()
            store.updateData { it.copy(login = null) }
        }
    }
}
