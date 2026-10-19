package doonv.jettop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val events: StateFlow<CachedDisciplineEvents?> field =
        MutableStateFlow<CachedDisciplineEvents?>(null)

    fun loadBlocking(): CachedDisciplineEvents? {
        val cached = cache.loadBlocking()
        if (cached != null) events.value = cached
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
        events.value = cached
        cached
    }

    suspend fun clear() {
        cache.clear()
        events.value = null
    }
}
