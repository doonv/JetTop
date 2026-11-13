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
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.loginDataStore: DataStore<LoginData?> by
dataStore(
    fileName = "login.json",
    serializer = LoginDataSerializer,
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    corruptionHandler = ReplaceFileCorruptionHandler { LoginDataSerializer.defaultValue },
)

object LoginDataSerializer : Serializer<LoginData?> {
    private val json = Json {
        ignoreUnknownKeys = true; isLenient = true
    }
    override val defaultValue: LoginData?
        get() = null

    override suspend fun readFrom(input: InputStream): LoginData? =
        try {
            json.decodeFromString(LoginData.serializer().nullable, input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read LoginData", serialization)
        }

    override suspend fun writeTo(t: LoginData?, output: OutputStream) {
        output.write(json.encodeToString(LoginData.serializer().nullable, t).encodeToByteArray())
    }

}
