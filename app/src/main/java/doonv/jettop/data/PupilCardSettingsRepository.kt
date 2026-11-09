package doonv.jettop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

class PupilCardSettingsRepository(
    private val api: WebtopApi = ApiClient.api,
    val cache: FileCache<PupilCardSettings>
) {
    val events: StateFlow<Cached<PupilCardSettings>?> field =
    MutableStateFlow<Cached<PupilCardSettings>?>(null)

    fun loadBlocking(): Cached<PupilCardSettings>? {
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
        cache.save(cached)
        events.value = cached
        cached
    }

    suspend fun clear() {
        cache.clear()
        events.value = null
    }
}
