package doonv.jettop.ui.studentcard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.data.DisciplineEventsRepository
import doonv.jettop.data.LoginData
import doonv.jettop.data.dataStore
import doonv.jettop.ui.LoadTrigger
import doonv.jettop.ui.Refreshable
import doonv.jettop.ui.UiState
import doonv.jettop.ui.load
import doonv.jettop.ui.startLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DisciplineEventsViewModel(
    app: Application,
    private val repository: DisciplineEventsRepository
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.dataStore
    val state: StateFlow<UiState<DisciplineEvents>> field =
    MutableStateFlow<UiState<DisciplineEvents>>(UiState.Loading)

    init {
        runBlocking {
            repository.loadBlocking()?.let { cached ->
                state.value = UiState.Success(cached.data)
            }
        }
        viewModelScope.launch {
            repository.events.collect { cached ->
                if (cached != null)
                    state.value = UiState.Success(cached.data)
            }
        }
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { login ->
                if (login != null) fetch(login, LoadTrigger.Automatic)
                else {
                    repository.clear()
                    state.value = UiState.Loading
                }
            }
        }
    }

    private suspend fun fetch(login: LoginData, trigger: LoadTrigger) {
        state.value = state.value.startLoading(trigger)
        state.value = state.value.load {
            repository.refresh(login).data
        }
    }

    override fun refresh() {
        viewModelScope.launch {
            store.data.map { it.login }.first()?.let { fetch(it, LoadTrigger.Refresh) }
        }
    }
}
