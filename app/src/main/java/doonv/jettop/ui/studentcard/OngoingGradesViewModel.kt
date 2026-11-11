package doonv.jettop.ui.studentcard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.Grade
import doonv.jettop.data.LoginData
import doonv.jettop.data.OngoingGradesRepository
import doonv.jettop.data.PupilCardDivision
import doonv.jettop.data.PupilCardSettings
import doonv.jettop.data.PupilCardSettingsRepository
import doonv.jettop.data.dataStore
import doonv.jettop.ui.LoadTrigger
import doonv.jettop.ui.Refreshable
import doonv.jettop.ui.UiState
import doonv.jettop.ui.load
import doonv.jettop.ui.map
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


enum class GradeSortOrder(val resource: Int) {
    Newest(R.string.newest),
    Oldest(R.string.oldest)
}

class OngoingGradesViewModel(
    app: Application,
    private val repository: OngoingGradesRepository,
    private val cardSettingsRepo: PupilCardSettingsRepository
) : AndroidViewModel(app), Refreshable {
    private val store = app.applicationContext.dataStore
    val gradesState: StateFlow<UiState<List<Grade>>> field =
    MutableStateFlow<UiState<List<Grade>>>(UiState.Loading)

    private val yearSettings = MutableStateFlow<PupilCardSettings?>(null)

    val divisions = yearSettings.map { settings ->
        settings?.divisionsList.orEmpty().sortedWith(
            compareBy<PupilCardDivision> { it.fromDate }
                .thenBy { it.toDate }
                .thenBy { it.divisionId }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val selectedDivisionId = MutableStateFlow<Int?>(null)
    val selectedYear = MutableStateFlow<Int?>(null)

    val sortOrder = MutableStateFlow(GradeSortOrder.Newest)

    val sortedGrades = combine(gradesState, sortOrder) { grades, sort ->
        when (sort) {
            GradeSortOrder.Newest -> grades.map { it -> it.sortedByDescending { it.date } }
            GradeSortOrder.Oldest -> grades.map { it -> it.sortedBy { it.date } }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = UiState.Loading
    )

    private val refreshRequests = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )

    init {
        val cachedSettings = cardSettingsRepo.events.value?.data
            ?: cardSettingsRepo.loadBlocking()?.data

        yearSettings.value = cachedSettings
        selectedDivisionId.value = defaultDivision(cachedSettings?.divisionsList)

        selectedYear.value = cachedSettings?.currentStudyYear

        val cached = repository.loadBlocking()
        val initialGrades = selectedYear.value?.let { year ->
            selectedDivisionId.value?.let { division ->
                cached?.data?.get(year)?.get(division)
            }
        }

        if (initialGrades != null) {
            gradesState.value = UiState.Success(initialGrades)
        }

        viewModelScope.launch {
            store.data
                .map { it.login }
                .distinctUntilChanged()
                .collectLatest { login ->
                    if (login == null) {
                        repository.clear()
                        yearSettings.value = null
                        selectedDivisionId.value = null
                        selectedYear.value = null
                        gradesState.value = UiState.Loading
                        return@collectLatest
                    }

                    cardSettingsRepo.events
                        .map { it?.data }
                        .distinctUntilChanged()
                        .collectLatest { settings ->
                            if (settings != null) observeYears(login, settings)
                        }
                }
        }
    }

    private suspend fun observeYears(login: LoginData, settings: PupilCardSettings) {
        val years = (settings.currentStudyYear - settings.numBackYearsAllowed)..
                settings.currentStudyYear
        selectedYear.update { current ->
            current?.takeIf { it in years } ?: settings.currentStudyYear
        }

        selectedYear.collectLatest { year ->
            if (year != null) observeYearLoads(login, year, settings)
        }
    }

    private suspend fun observeYearLoads(
        login: LoginData,
        year: Int,
        defaultSettings: PupilCardSettings
    ) {
        val cachedSettings = cardSettingsRepo.cachedSettingsForYear(year)
            ?: defaultSettings.takeIf { year == it.currentStudyYear }
        cachedSettings?.let {
            yearSettings.value = it
            selectValidDivision(it.divisionsList)
        }
        gradesState.value = cachedGradesState(year, selectedDivisionId.value)

        val selectionLoads = selectedDivisionId.map { GradeLoad(it, LoadTrigger.Automatic) }
        val manualLoads = refreshRequests.map {
            GradeLoad(selectedDivisionId.value, LoadTrigger.Refresh)
        }
        merge(selectionLoads, manualLoads).collectLatest { request ->
            loadGrades(login, year, cachedSettings, request)
        }
    }

    private data class GradeLoad(val division: Int?, val trigger: LoadTrigger)

    private suspend fun loadGrades(
        login: LoginData,
        year: Int,
        cachedSettings: PupilCardSettings?,
        request: GradeLoad
    ) {
        gradesState.value = cachedGradesState(year, request.division, request.trigger)
        when (val loaded = loadYearSettings(login, year, cachedSettings, request.trigger)) {
            is UiState.Success -> {
                yearSettings.value = loaded.data
                selectValidDivision(loaded.data.divisionsList)
                val division = selectedDivisionId.value
                if (division == null) {
                    gradesState.value = UiState.Success(emptyList())
                } else if (division == request.division) {
                    fetch(login, loaded.data, division, year)
                }
            }
            is UiState.Error -> gradesState.value = loaded
            UiState.Loading -> gradesState.value = UiState.Loading
        }
    }

    private suspend fun loadYearSettings(
        login: LoginData,
        year: Int,
        fallback: PupilCardSettings?,
        trigger: LoadTrigger
    ): UiState<PupilCardSettings> {
        val cachedSettings = cardSettingsRepo.cachedSettingsForYear(year) ?: fallback
        val cachedState = cachedSettings?.let { UiState.Success(it) } ?: UiState.Loading
        if (trigger != LoadTrigger.Refresh && cachedSettings != null) return cachedState

        return cachedState.load { cardSettingsRepo.settingsForYear(login, year) }
    }

    private fun selectValidDivision(divisions: List<PupilCardDivision>) {
        selectedDivisionId.update { current ->
            current?.takeIf { id -> divisions.any { it.divisionId == id } }
                ?: defaultDivision(divisions)
                ?: divisions.firstOrNull()?.divisionId
        }
    }

    private fun cachedGradesState(
        year: Int,
        division: Int?,
        trigger: LoadTrigger? = null
    ): UiState<List<Grade>> = repository.grades.value?.data?.get(year)?.get(division)?.let {
        UiState.Success(it, loadTrigger = trigger)
    } ?: UiState.Loading

    private fun defaultDivision(divisions: List<PupilCardDivision>?): Int? =
        divisions?.find { it.isCurrent }?.divisionId


    private suspend fun fetch(
        login: LoginData,
        settings: PupilCardSettings,
        division: Int,
        year: Int
    ) {
        gradesState.value = gradesState.value.load {
            repository.refreshDivision(login, settings, division, year)
                .data.getValue(year).getValue(division)
        }
    }


    override fun refresh() {
        refreshRequests.tryEmit(Unit)
    }
}
