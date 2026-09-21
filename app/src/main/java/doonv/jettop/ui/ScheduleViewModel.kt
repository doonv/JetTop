package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.ApiClient
import doonv.jettop.data.ApiConfig
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleRequest
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

sealed interface ScheduleUiState {
    data object Loading : ScheduleUiState
    data class Success(val days: List<DaySchedule>, val firstName: String) : ScheduleUiState
    data class Error(val message: String) : ScheduleUiState
}

class ScheduleViewModel(app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _state = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val state: StateFlow<ScheduleUiState> = _state

    init {
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { loginData ->
                if (loginData == null) return@collect
                else fetch(loginData)
            }
        }
    }

    private suspend fun fetch(loginData: LoginData) {
        _state.value = ScheduleUiState.Loading
        val cookie = loginData.cookie()
        val institutionCode = loginData.institutionCode
        val classCode = loginData.classCode
        if (loginData.token.isBlank() || institutionCode == 0 || classCode.isBlank()) {
            _state.value = ScheduleUiState.Error(
                getApplication<Application>().getString(R.string.schedule_error_missing_details)
            )
            return
        }
        try {
            val resp = ApiClient.api.getSchedule(
                ScheduleRequest(
                    institutionCode = institutionCode,
                    selectedValue = "$classCode|${loginData.classNumber ?: 100}",
                    typeView = ApiConfig.TYPE_VIEW
                ), cookie
            )
            // When our token is expired, WebTop's backend returns an empty week with no lessons.
            // I don't think this is distinguishable from an actual valid empty week,
            // so we call the checkToken API to make sure.
            val isEmpty = resp.data.all { day ->
                day.hoursData.all {
                    it.schedule.isEmpty() && it.events.isEmpty() && it.exams.isEmpty()
                }
            }
            if (isEmpty) {
                val isValid = ApiClient.api.checkToken(cookie).data
                if (!isValid) logout()
            }

            _state.value = if (resp.status) ScheduleUiState.Success(resp.data, loginData.firstName)
            else ScheduleUiState.Error("API returned status=false")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            _state.value = ScheduleUiState.Error(e.message ?: "Unknown error")
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val login = store.data.map { it.login }.first() ?: return@launch
            fetch(login)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            store.updateData { it.copy(login = null) }
        }
    }
}

