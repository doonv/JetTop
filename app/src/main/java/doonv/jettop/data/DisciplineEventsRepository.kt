package doonv.jettop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.io.IOException

@Serializable
data class CachedDisciplineEvents(
    val events: DisciplineEvents,
    val updatedAt: Long
)

// TODO: dedupe discipline fetches. The dashboard and student-card ViewModels both
//       fetch on login, so startup issues two identical requests.
class DisciplineEventsRepository(
    private val api: WebtopApi = ApiClient.api,
    val cache: DisciplineFileCache
) {
    private val _events = MutableStateFlow<CachedDisciplineEvents?>(null)
    val events: StateFlow<CachedDisciplineEvents?> = _events.asStateFlow()

    fun loadBlocking(): CachedDisciplineEvents? {
        val cached = cache.loadBlocking()
        if (cached != null) _events.value = cached
        return cached
    }

    suspend fun refresh(login: LoginData): CachedDisciplineEvents = withContext(Dispatchers.IO) {
        val response = api.getDisciplineEvents(
            DisciplineEventsRequest(studentID = login.userId),
            login.cookie()
        )
        if (!response.status)
            throw IOException(response.message ?: "API returned status=false")

        val cached = CachedDisciplineEvents(
            events = response.data,
            updatedAt = System.currentTimeMillis()
        )
        cache.save(cached)
        _events.value = cached
        cached
    }

    suspend fun clear() {
        cache.clear()
        _events.value = null
    }
}
