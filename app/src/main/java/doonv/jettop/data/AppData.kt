package doonv.jettop.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.dataStore: DataStore<AppData> by
dataStore(
    fileName = "data.json",
    serializer = AppDataSerializer,
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    corruptionHandler = ReplaceFileCorruptionHandler { AppDataSerializer.defaultValue },
)

@Serializable
data class AppData(
    val login: LoginData?
)

object AppDataSerializer : Serializer<AppData> {
    private val json = Json {
        ignoreUnknownKeys = true; isLenient = true
    }
    override val defaultValue: AppData
        get() = AppData(login = null)

    override suspend fun readFrom(input: InputStream): AppData =
        try {
            json.decodeFromString(AppData.serializer(), input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read AppData", serialization)
        }

    override suspend fun writeTo(t: AppData, output: OutputStream) {
        output.write(json.encodeToString(AppData.serializer(), t).encodeToByteArray())
    }

}