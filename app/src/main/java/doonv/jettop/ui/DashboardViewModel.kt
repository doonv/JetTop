package doonv.jettop.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.Cached
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.data.DisciplineEventsRepository
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleRepository
import doonv.jettop.data.TokenExpiredException
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
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
import java.io.IOException

data class DashboardData(
    val schedule: Cached<List<DaySchedule>>,
    val disciplineEvents: DisciplineEvents,
)

private sealed interface LoadState<out T> {
    data object Loading : LoadState<Nothing>
    data class Loaded<T>(val value: T) : LoadState<T>
    data class Failed(val message: String) : LoadState<Nothing>
}

class DashboardViewModel(
    app: Application,
    private val scheduleRepository: ScheduleRepository,
    private val disciplineRepository: DisciplineEventsRepository,
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.dataStore

    private val scheduleState = MutableStateFlow<LoadState<Cached<List<DaySchedule>>>>(LoadState.Loading)
    private val disciplineState = MutableStateFlow<LoadState<DisciplineEvents>>(LoadState.Loading)
    private val refreshing = MutableStateFlow(false)

    init {
        runBlocking {
            val login = store.data.map { it.login }.first()
            val cachedSchedule = scheduleRepository.cache.loadBlocking()
            if (login != null && cachedSchedule != null)
                scheduleState.value = LoadState.Loaded(cachedSchedule)
            disciplineRepository.loadBlocking()?.let { cached ->
                disciplineState.value = LoadState.Loaded(cached.data)
            }
        }
        viewModelScope.launch {
            disciplineRepository.events.collect { cached ->
                if (cached != null)
                    disciplineState.value = LoadState.Loaded(cached.data)
            }
        }
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { login ->
                if (login == null) {
                    disciplineRepository.clear()
                    disciplineState.value = LoadState.Loading
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
        schedule: LoadState<Cached<List<DaySchedule>>>,
        discipline: LoadState<DisciplineEvents>,
        refreshing: Boolean,
    ): UiState<DashboardData> = when {
        schedule is LoadState.Failed -> UiState.Error(schedule.message)
        discipline is LoadState.Failed -> UiState.Error(discipline.message)
        schedule is LoadState.Loaded && discipline is LoadState.Loaded -> UiState.Success(
            data = DashboardData(
                schedule = schedule.value,
                disciplineEvents = discipline.value,
            ),
            isRefreshing = refreshing,
        )

        else -> UiState.Loading
    }

    private suspend fun fetchSchedule(login: LoginData) {
        try {
            val fresh = scheduleRepository.refresh(login)
            scheduleState.value = LoadState.Loaded(fresh)
        } catch (e: CancellationException) {
            throw e
        } catch (e: TokenExpiredException) {
            scheduleRepository.cache.clear()
            logout()
        } catch (e: IOException) {
            if (scheduleState.value !is LoadState.Loaded)
                scheduleState.value = LoadState.Failed("You're offline.")
        } catch (e: Exception) {
            scheduleState.value =
                LoadState.Failed(e.localizedMessage ?: e.message ?: "Unknown error")
        }
    }

    private suspend fun fetchDiscipline(login: LoginData) {
        try {
            val fresh = disciplineRepository.refresh(login)
            disciplineState.value = LoadState.Loaded(fresh.data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Log.e("DashboardViewModel", e.toString())
            if (disciplineState.value !is LoadState.Loaded)
                disciplineState.value = LoadState.Failed(
                    e.localizedMessage ?: e.message
                    ?: getApplication<Application>().getString(R.string.unknown_error)
                )
        } catch (e: Exception) {
            Log.e("DashboardViewModel", e.toString())
            disciplineState.value = LoadState.Failed(
                e.localizedMessage ?: e.message
                ?: getApplication<Application>().getString(R.string.unknown_error)
            )
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
            scheduleState.value = LoadState.Loading
            scheduleRepository.cache.clear()
            store.updateData { it.copy(login = null) }
        }
    }
}
