package doonv.jettop.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

object ApiConfig {
    const val BASE_URL = "https://webtopserver.smartschool.co.il/"
    const val TYPE_VIEW = 1
}

interface WebtopApi {
    @POST("server/api/user/LoginByUserNameAndPassword")
    suspend fun login(@Body body: LoginRequest): ApiResponse<LoginData>

    @POST("server/api/shotef/ShotefSchedualeData")
    suspend fun getSchedule(
        @Body body: ScheduleRequest,
        @Header("Cookie") cookie: String
    ): ApiResponse<List<DaySchedule>>

    /**
     * @return if the current `webToken` in `cookie` is valid.
     */
    @POST("server/api/dashboard/CheckToken")
    suspend fun checkToken(
        @Header("Cookie") cookie: String
    ): ApiResponse<Boolean>

    @POST("server/api/Menu/GetMenuCounters")
    suspend fun getMenuCounters(
        @Header("Cookie") cookie: String
    ): ApiResponse<MenuCounters>

    /**
     * Gets a list of up to 30 messages from your inbox.
     */
    @POST("server/api/messageBox/GetMessagesInbox")
    suspend fun getMessagesInbox(
        @Body body: MessagesInboxRequest,
        @Header("Cookie") cookie: String
    ): ApiResponse<List<InboxMessage>>

    /**
     * Gets a specific message's details. Also marks it as read.
     */
    @POST("server/api/messageBox/GetMessagesInboxData")
    suspend fun getMessage(
        @Body body: MessageDetailsRequest,
        @Header("Cookie") cookie: String
    ): ApiResponse<MessageDetails>
}

/**
 * Converts all fields in the annotated class to PascalCase when serialized.
 */
// TODO: yknow I never actually checked if the server cares about case
//       I might be doing this for nothing
@OptIn(ExperimentalSerializationApi::class)
@SerialInfo
@Target(AnnotationTarget.CLASS)
annotation class PascalCase

object ApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
        encodeDefaults = true
        namingStrategy = JsonNamingStrategy { descriptor, _, serialName ->
            if (descriptor.annotations.any { it is PascalCase })
                serialName.replaceFirstChar { it.uppercaseChar() }
            else serialName
        }
    }

    private val http = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (X11; Linux x86_64; rv:155.0) Gecko/20100101 Firefox/155.0"
                    )
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("language", "he")
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
