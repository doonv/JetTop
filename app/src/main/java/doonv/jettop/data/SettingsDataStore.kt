package doonv.jettop.data

import android.content.Context
import androidx.annotation.StringRes
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import doonv.jettop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.settingsDataStore: DataStore<Settings> by
dataStore(
    fileName = "settings.json",
    serializer = SettingsSerializer,
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    corruptionHandler = ReplaceFileCorruptionHandler { SettingsSerializer.defaultValue },
)

enum class DateType(@param:StringRes val resource: Int) {
    Gregorian(R.string.date_type_gregorian),
    Hebrew(R.string.date_type_hebrew)
}

@Serializable
data class Settings(
    val useSystemFont: Boolean = false,
    val dateType: DateType = DateType.Gregorian,
)

object SettingsSerializer : Serializer<Settings> {
    private val json = Json {
        ignoreUnknownKeys = true; isLenient = true
    }
    override val defaultValue: Settings
        get() = Settings()

    override suspend fun readFrom(input: InputStream): Settings =
        try {
            json.decodeFromString(Settings.serializer(), input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        output.write(json.encodeToString(Settings.serializer(), t).encodeToByteArray())
    }

}
