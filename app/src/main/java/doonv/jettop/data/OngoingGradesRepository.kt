package doonv.jettop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

typealias GradeData = Map<Int, Map<Int, List<Grade>>>

class OngoingGradesRepository(
    private val api: WebtopApi = ApiClient.api,
    val cache: FileCache<GradeData>
) {
    val grades: StateFlow<Cached<GradeData>?> field =
    MutableStateFlow<Cached<GradeData>?>(null)

    fun loadBlocking(): Cached<GradeData>? {
        val cached = cache.loadBlocking()
        if (cached != null) grades.value = cached
        return cached
    }

    suspend fun refreshDivision(
        login: LoginData,
        settings: PupilCardSettings,
        division: Int,
        year: Int
    ): Cached<GradeData> = withContext(Dispatchers.IO) {
        fetchAndCache(division, year, settings, login) { division, data ->
            val current = grades.value?.data.orEmpty()
            current + (year to (current[year].orEmpty() + (division to data)))
        }
    }

    suspend fun refresh(
        login: LoginData,
        settings: PupilCardSettings,
        division: Int,
        year: Int
    ): Cached<GradeData> = withContext(Dispatchers.IO) {
        fetchAndCache(division, year, settings, login) { division, data ->
            mapOf(year to mapOf(division to data))
        }
    }

    private suspend fun fetchAndCache(
        division: Int,
        year: Int,
        settings: PupilCardSettings,
        login: LoginData,
        getData: (Int, List<Grade>) -> GradeData
    ): Cached<GradeData> {
        val response = api.getGrades(
            GradesRequest(
                studentID = login.userId,
    //                weekIndex = TODO(),
    //                viewType = TODO(),
                studyYear = year,
    //                studyYearName = TODO(),
    //                studentName = TODO(),
                classCode = login.classCode,
                periodID = division,
    //                periodName = TODO(),
                moduleID = settings.modulesList.find { it.universalTitle == "grades" }?.id!!, // FIXME: This is dumb
            ),
            login.cookie()
        )
        if (!response.status)
            throw IOException(response.message ?: "API returned status=false")
        val cached = Cached(
            data = getData(division, response.data),
            updatedAt = System.currentTimeMillis()
        )
        cache.save(cached)
        grades.value = cached
        return cached
    }

    suspend fun clear() {
        cache.clear()
        grades.value = null
    }
}
