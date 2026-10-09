package doonv.jettop.ui.studentcard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.ApiClient
import doonv.jettop.data.LessonEvents
import doonv.jettop.data.LessonEventsRequest
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ClassEventsUiState {
    fun refreshing(): Boolean = this is Success && this.isRefreshing

    data object Loading : ClassEventsUiState
    data class Success(
        val events: LessonEvents,
        val isRefreshing: Boolean = false
    ) : ClassEventsUiState

    data class Error(val message: String) : ClassEventsUiState
}

class ClassEventsViewModel(app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _state = MutableStateFlow<ClassEventsUiState>(ClassEventsUiState.Loading)
    val state: StateFlow<ClassEventsUiState> = _state
    fun load() {
        (_state.value as? ClassEventsUiState.Success)?.let {
            _state.value = it.copy(isRefreshing = true)
        }
        viewModelScope.launch {
            try {
                val login = store.data.map { it.login }.first()
                if (login == null)
                    return@launch // probably unreachable
                val response = ApiClient.api.getEvents(
                    LessonEventsRequest(studentID = login.userId),
                    login.cookie()
                )
                if (!response.status)
                    throw IOException(response.message ?: "API returned status=false")

                _state.value = ClassEventsUiState.Success(response.data)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ClassEventsViewModel", e.toString())
                _state.value = ClassEventsUiState.Error(
                    e.localizedMessage ?: e.message
                    ?: getApplication<Application>().getString(R.string.unknown_error)
                )
            }
        }
    }
}