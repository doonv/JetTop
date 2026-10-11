package doonv.jettop.ui.studentcard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.ApiClient
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.data.DisciplineEventsRequest
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DisciplineEventsUiState {
    fun refreshing(): Boolean = this is Success && this.isRefreshing

    data object Loading : DisciplineEventsUiState
    data class Success(
        val events: DisciplineEvents,
        val isRefreshing: Boolean = false
    ) : DisciplineEventsUiState

    data class Error(val message: String) : DisciplineEventsUiState
}

class DisciplineEventsViewModel(app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _state = MutableStateFlow<DisciplineEventsUiState>(DisciplineEventsUiState.Loading)
    val state: StateFlow<DisciplineEventsUiState> = _state
    fun load() {
        (_state.value as? DisciplineEventsUiState.Success)?.let {
            _state.value = it.copy(isRefreshing = true)
        }
        viewModelScope.launch {
            try {
                val login = store.data.map { it.login }.first()
                if (login == null)
                    return@launch // probably unreachable
                val response = ApiClient.api.getDisciplineEvents(
                    DisciplineEventsRequest(studentID = login.userId),
                    login.cookie()
                )
                if (!response.status)
                    throw IOException(response.message ?: "API returned status=false")

                _state.value = DisciplineEventsUiState.Success(response.data)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("DisciplineEventsViewModel", e.toString())
                _state.value = DisciplineEventsUiState.Error(
                    e.localizedMessage ?: e.message
                    ?: getApplication<Application>().getString(R.string.unknown_error)
                )
            }
        }
    }
}