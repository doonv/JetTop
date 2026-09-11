package doonv.smarterschool.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import doonv.smarterschool.data.ApiClient
import doonv.smarterschool.data.ApiConfig
import doonv.smarterschool.data.DaySchedule
import doonv.smarterschool.data.ScheduleRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ScheduleUiState {
    data object Loading : ScheduleUiState
    data class Success(val days: List<DaySchedule>) : ScheduleUiState
    data class Error(val message: String) : ScheduleUiState
}

class ScheduleViewModel : ViewModel() {
    private val _state = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val state: StateFlow<ScheduleUiState> = _state

    init {
        refresh()
    }

    fun refresh() {
        _state.value = ScheduleUiState.Loading
        viewModelScope.launch {
            try {
                val resp = ApiClient.api.getSchedule(
                    ScheduleRequest(
                        institutionCode = ApiConfig.INSTITUTION_CODE,
                        selectedValue = ApiConfig.SELECTED_VALUE,
                        typeView = ApiConfig.TYPE_VIEW
                    )
                )
                if (resp.status) {
                    _state.value = ScheduleUiState.Success(resp.data)
                } else {
                    _state.value = ScheduleUiState.Error("API returned status=false")
                }
            } catch (e: Exception) {
                _state.value = ScheduleUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
