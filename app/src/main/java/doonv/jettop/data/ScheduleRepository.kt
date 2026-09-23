package doonv.jettop.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.io.IOException

@Serializable
data class CachedSchedule(
    val days: List<DaySchedule>,
    val updatedAt: Long
)

class ScheduleRepository(
    private val api: WebtopApi = ApiClient.api,
    public val cache: ScheduleFileCache
) {
    suspend fun refresh(login: LoginData): CachedSchedule = withContext(Dispatchers.IO) {
        val cookie = login.cookie()
        val resp = api.getSchedule(
            ScheduleRequest(
                institutionCode = login.institutionCode,
                selectedValue = "${login.classCode}|${login.classNumber ?: 100}",
                typeView = ApiConfig.TYPE_VIEW
            ),
            cookie
        )
        if (!resp.status) throw IOException(resp.message ?: "API returned status=false")

        // When our token is expired, WebTop's backend returns an empty week with no lessons.
        // I don't think this is distinguishable from an actual valid empty week,
        // so we call the checkToken API to make sure.
        val isEmpty = resp.data.all { day ->
            day.hoursData.all {
                it.schedule.isEmpty() && it.events.isEmpty() && it.exams.isEmpty()
            }
        }
        if (isEmpty && !api.checkToken(cookie).data) throw TokenExpiredException()

        val cached = CachedSchedule(days = resp.data, updatedAt = System.currentTimeMillis())
        cache.save(cached)
        cached
    }
}

class TokenExpiredException : Exception("Token expired")
