package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.ApiClient
import doonv.jettop.data.LoginData
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuCountersViewModel(app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore

    private val _unreadMessages = MutableStateFlow<Int?>(null)
    val unreadMessages: StateFlow<Int?> = _unreadMessages

    init {
        viewModelScope.launch {
            store.data.map { it.login }.distinctUntilChanged().collect { loginData ->
                if (loginData == null) {
                    _unreadMessages.value = null
                    return@collect
                }
                fetch(loginData)
            }
        }
    }

    private suspend fun fetch(loginData: LoginData) {
        try {
            val resp = ApiClient.api.getMenuCounters(loginData.cookie())
            if (resp.status)
                _unreadMessages.value = resp.data.unreadMessages
        } catch (e: Exception) {
            if (e is CancellationException) throw e
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val login = store.data.map { it.login }.first() ?: run {
                _unreadMessages.value = null
                return@launch
            }
            fetch(login)
        }
    }

    /**
     * Mark a message as read, decrementing the counter
     */
    fun markRead() {
        _unreadMessages.update { it?.minus(1)?.coerceAtLeast(0) }
    }
}
