package doonv.jettop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

// TODO: dedupe discipline fetches. The dashboard and student-card ViewModels both
//       fetch on login, so startup issues two identical requests.
class DisciplineEventsRepository(
    private val api: WebtopApi = ApiClient.api,
    val cache: FileCache<DisciplineEvents>
) {
    val events: StateFlow<Cached<DisciplineEvents>?> field =
        MutableStateFlow<Cached<DisciplineEvents>?>(null)

    fun loadBlocking(): Cached<DisciplineEvents>? {
        val cached = cache.loadBlocking()
        if (cached != null) events.value = cached
        return cached
    }

    suspend fun refresh(login: LoginData): Cached<DisciplineEvents> = withContext(Dispatchers.IO) {
        val response = api.getDisciplineEvents(
            DisciplineEventsRequest(studentID = login.userId),
            login.cookie()
        )
        if (!response.status)
            throw IOException(response.message ?: "API returned status=false")

        val cached = Cached(
            data = response.data,
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
