package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import doonv.jettop.data.ApiClient
import doonv.jettop.data.InboxMessage
import doonv.jettop.data.MessagesPagingSource
import doonv.jettop.data.WebtopApi
import doonv.jettop.data.dataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MessagesViewModel(app: Application) : AndroidViewModel(app) {
    private val api: WebtopApi = ApiClient.api
    private val store = app.applicationContext.dataStore

    private val _locallyRead = MutableStateFlow<Set<String>>(emptySet())
    val locallyRead: StateFlow<Set<String>> = _locallyRead.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val messages: Flow<PagingData<InboxMessage>> =
        store.data.map { it.login }
            .distinctUntilChanged()
            .flatMapLatest { login ->
                if (login == null) return@flatMapLatest emptyFlow()
                Pager(PagingConfig(pageSize = 15, prefetchDistance = 5)) {
                    MessagesPagingSource(api, login.cookie(), 0, "")
                }.flow
            }
            .cachedIn(viewModelScope)

    fun markRead(messageId: String) = _locallyRead.update { it + messageId }
}