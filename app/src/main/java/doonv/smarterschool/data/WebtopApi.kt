package doonv.smarterschool.data

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

object ApiConfig {
    const val BASE_URL = "https://webtopserver.smartschool.co.il/"
    const val INSTITUTION_CODE = -123456
    const val SELECTED_VALUE = "GRADE|CLASS"
    const val TYPE_VIEW = 1
    const val COOKIE: String = doonv.smarterschool.BuildConfig.WEB_COOKIE
}

interface WebtopApi {
    @POST("server/api/shotef/ShotefSchedualeData")
    suspend fun getSchedule(
        @Body body: ScheduleRequest,
        @Header("Cookie") cookie: String = ApiConfig.COOKIE,
        @Header("language") language: String = "he",
        @Header("rememberMe") rememberMe: String = "0",
        @Header("X-XSRF-TOKEN") xsrf: String = "",
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Origin") origin: String = "https://webtop.smartschool.co.il",
        @Header("Referer") referer: String = "https://webtop.smartschool.co.il/",
        @Header("Sec-GPC") gpc: String = "1",
        @Header("Sec-Fetch-Dest") fetchDest: String = "empty",
        @Header("Sec-Fetch-Mode") fetchMode: String = "cors",
        @Header("Sec-Fetch-Site") fetchSite: String = "same-site"
    ): ScheduleResponse
}

object ApiClient {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
        encodeDefaults = true
    }

    private val http = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:155.0) Gecko/20100101 Firefox/155.0")
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .build()
            )
        }
        .build()

    val api: WebtopApi = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .client(http)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(WebtopApi::class.java)
}
