package doonv.jettop.ui

import android.app.Application
import android.app.DownloadManager
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.ApiClient
import doonv.jettop.data.AttachedFile
import doonv.jettop.data.MessageDetails
import doonv.jettop.data.MessageDetailsRequest
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MessageDetailsUiState {
    data object Loading : MessageDetailsUiState
    data class Success(val message: MessageDetails) : MessageDetailsUiState
    data class Error(val message: String) : MessageDetailsUiState
}

class MessageDetailsViewModel(private val app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _state = MutableStateFlow<MessageDetailsUiState>(MessageDetailsUiState.Loading)
    val state: StateFlow<MessageDetailsUiState> = _state
    fun load(messageId: String) {
        viewModelScope.launch {
            try {
                val login = store.data.map { it.login }.first()
                if (login == null)
                    return@launch // probably unreachable
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

                _state.value = MessageDetailsUiState.Success(response.data)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("MessageDetailsViewModel", e.toString())
                _state.value = MessageDetailsUiState.Error(
                    e.localizedMessage ?: e.message
                    ?: getApplication<Application>().getString(R.string.unknown_error)
                )
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