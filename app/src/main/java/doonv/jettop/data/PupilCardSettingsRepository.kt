package doonv.jettop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.IOException

class PupilCardSettingsRepository(
    private val api: WebtopApi = ApiClient.api,
    val cache: FileCache<PupilCardSettings>,
    private val yearCache: FileCache<Map<Int, PupilCardSettings>>
) {
    val events: StateFlow<Cached<PupilCardSettings>?> field =
    MutableStateFlow<Cached<PupilCardSettings>?>(null)

    fun loadBlocking(): Cached<PupilCardSettings>? {
        yearSettings.value = yearCache.loadBlocking()?.data.orEmpty()
        val cached = cache.loadBlocking()
        if (cached != null) events.value = cached
        return cached
    }

    suspend fun refresh(login: LoginData): Cached<PupilCardSettings> = withContext(Dispatchers.IO) {
        val response = api.getPupilCardSettings(
            PupilCardSettingsRequest(),
            login.cookie()
        )
        if (!response.status)
            throw IOException(response.message ?: "API returned status=false")

        val cached = Cached(
            data = response.data,
            updatedAt = System.currentTimeMillis()
        )
        saveYear(response.data.currentStudyYear, response.data)
        cache.save(cached)
        events.value = cached
        cached
    }

    private val yearSettings = MutableStateFlow<Map<Int, PupilCardSettings>>(emptyMap())
    private val yearCacheMutex = Mutex()

    private suspend fun saveYear(year: Int, settings: PupilCardSettings) {
        yearCacheMutex.withLock {
            val updated = yearSettings.value + (year to settings)
            yearCache.save(Cached(updated, System.currentTimeMillis()))
            yearSettings.value = updated
        }
    }

    suspend fun settingsForYear(login: LoginData, year: Int): PupilCardSettings {
        val response = api.getPupilCardSettings(
            PupilCardSettingsRequest(studyYear = year),
            login.cookie()
        )
        if (!response.status)
            throw IOException(response.message ?: "API returned status=false")
        saveYear(year, response.data)
        return response.data
    }

    fun cachedSettingsForYear(year: Int): PupilCardSettings? = yearSettings.value[year]

    suspend fun clear() {
        yearCacheMutex.withLock {
            yearSettings.value = emptyMap()
            yearCache.clear()
        }
        cache.clear()
        events.value = null
    }
}
