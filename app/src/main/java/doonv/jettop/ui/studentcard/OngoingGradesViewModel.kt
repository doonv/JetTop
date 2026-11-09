package doonv.jettop.ui.studentcard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.Grade
import doonv.jettop.data.LoginData
import doonv.jettop.data.OngoingGradesRepository
import doonv.jettop.data.PupilCardDivision
import doonv.jettop.data.PupilCardSettings
import doonv.jettop.data.PupilCardSettingsRepository
import doonv.jettop.data.dataStore
import doonv.jettop.ui.Refreshable
import doonv.jettop.ui.UiState
import doonv.jettop.ui.load
import doonv.jettop.ui.startRefreshing
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OngoingGradesViewModel(
    app: Application,
    private val repository: OngoingGradesRepository,
    private val cardSettingsRepo: PupilCardSettingsRepository
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.dataStore
    val state: StateFlow<UiState<List<Grade>>> field =
    MutableStateFlow<UiState<List<Grade>>>(UiState.Loading)

    val selectedDivisionId = MutableStateFlow<Int?>(null)
    val selectedYear = MutableStateFlow<Int?>(null)

    private val refreshRequests = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )

    init {
        val cachedSettings = cardSettingsRepo.events.value?.data
            ?: cardSettingsRepo.loadBlocking()?.data

        selectedDivisionId.value = defaultDivision(cachedSettings?.divisionsList)

        selectedYear.value = cachedSettings?.currentStudyYear

        val cached = repository.loadBlocking()
        val initialGrades = selectedYear.value?.let { year ->
            selectedDivisionId.value?.let { division ->
                cached?.data?.get(year)?.get(division)
            }
        }

        if (initialGrades != null) {
            state.value = UiState.Success(initialGrades)
        }

        viewModelScope.launch {
            store.data
                .map { it.login }
                .distinctUntilChanged()
                .collectLatest { login ->
                    if (login == null) {
                        repository.clear()
                        selectedDivisionId.value = null
                        selectedYear.value = null
                        state.value = UiState.Loading
                        return@collectLatest
                    }

                    cardSettingsRepo.events
                        .map { it?.data }
                        .distinctUntilChanged()
                        .collectLatest settingsChanged@{ settings ->
                            if (settings == null) return@settingsChanged

                            val divisions = settings.divisionsList
                            selectedDivisionId.update { current ->
                                current?.takeIf { id ->
                                    divisions.any { it.divisionId == id }
                                } ?: defaultDivision(divisions)
                            }

                            val years = (settings.currentStudyYear - settings.numBackYearsAllowed)..
                                    settings.currentStudyYear
                            selectedYear.update { current ->
                                current?.takeIf { it in years } ?: settings.currentStudyYear
                            }

                            combine(selectedDivisionId, selectedYear) { division, year ->
                                division to year
                            }.collectLatest selectionChanged@{ (division, year) ->
                                if (division == null || year == null) return@selectionChanged

                                val grades = repository.grades.value
                                    ?.data
                                    ?.get(year)
                                    ?.get(division)

                                state.value = if (grades != null) {
                                    UiState.Success(grades)
                                } else {
                                    UiState.Loading
                                }

                                refreshRequests
                                    .map { true }                // Manual refresh
                                    .onStart { emit(false) }     // Selection changed
                                    .collectLatest { isManualRefresh ->
                                        if (isManualRefresh) {
                                            state.value = state.value.startRefreshing()
                                        }
                                        fetch(login, settings, division, year)
                                    }
                            }
                        }
                }
        }
    }

    private fun defaultDivision(divisions: List<PupilCardDivision>?): Int? =
        divisions?.find { it.isCurrent }?.divisionId


    private suspend fun fetch(
        login: LoginData,
        settings: PupilCardSettings,
        division: Int,
        year: Int
    ) {
        state.value = state.value.load {
            repository.refreshDivision(login, settings, division, year)
                .data.getValue(year).getValue(division)
        }
    }


    override fun refresh() {
        refreshRequests.tryEmit(Unit)
    }
}
