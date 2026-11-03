package doonv.jettop.data

import androidx.core.util.AtomicFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.File

class FileCache<T>(
    private val file: File,
    private val serializer: KSerializer<Cached<T>>,
    private val json: Json = Json {
        ignoreUnknownKeys = true; isLenient = true
    }
) {
    private val atomic = AtomicFile(file);
    private val lock = Any()

    fun loadBlocking(): Cached<T>? = synchronized(lock) {
        try {
            if (!file.exists()) null else json.decodeFromString(serializer, file.readText())
        } catch (e: Exception) {
            null
        }
    }

    suspend fun save(v: Cached<T>) = withContext(Dispatchers.IO) {
        synchronized(lock) {
            val stream = atomic.startWrite()
            try {
                stream.write(json.encodeToString(serializer, v).toByteArray());
                atomic.finishWrite(stream);
            } catch (t: Throwable) {
                atomic.failWrite(stream)
                throw t
            }
        }
    }


    suspend fun clear() = withContext(Dispatchers.IO) {
        synchronized(lock) {
            atomic.delete()
        }
    }
}
