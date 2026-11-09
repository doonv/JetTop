package doonv.jettop.ui

import android.app.Application
import android.app.DownloadManager
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.ApiClient
import doonv.jettop.data.AttachedFile
import doonv.jettop.data.MessageDetails
import doonv.jettop.data.MessageDetailsRequest
import doonv.jettop.data.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class MessageDetailsViewModel(private val app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    val state: StateFlow<UiState<MessageDetails>> field =
        MutableStateFlow<UiState<MessageDetails>>(UiState.Loading)
    fun load(messageId: String) {
        viewModelScope.launch {
            state.value = UiState.Loading
            state.value = state.value.load {
                val login = checkNotNull(store.data.map { it.login }.first()) {
                    "Login required to load a message"
                }
                val response = ApiClient.api.getMessage(
                    MessageDetailsRequest(
                        filterId = 0,
                        isInBox = true,
                        messageId = messageId
                    ),
                    login.cookie()
                )
                if (!response.status)
                    throw IOException(response.message ?: "API returned status=false")

                response.data
            }
        }
    }

    fun downloadAttachment(file: AttachedFile) {
        // TODO: Change the notification to appear as if it came from
        //       JetTop, not from DownloadManager.
        viewModelScope.launch {
            val login = store.data.map { it.login }.first() ?: return@launch
            val request = DownloadManager.Request(file.fileUrl.toUri()).apply {
                setTitle(file.fileName)
                addRequestHeader("Cookie", login.cookie())
                setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                )
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.fileName)
            }
            runCatching { app.getSystemService(DownloadManager::class.java).enqueue(request) }
                .onFailure { Log.e("MessageDetailsViewModel", "enqueue failed", it) }
        }
    }
}