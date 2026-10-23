package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import doonv.jettop.data.ApiClient
import doonv.jettop.data.ApiConfig
import doonv.jettop.data.InboxMessage
import doonv.jettop.data.LoginData
import doonv.jettop.data.MessagesPagingSource
import doonv.jettop.data.WebtopApi
import doonv.jettop.data.dataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MessagesViewModel(app: Application) : AndroidViewModel(app) {
    private val api: WebtopApi = ApiClient.api
    private val store = app.applicationContext.dataStore

    val locallyRead: StateFlow<Set<String>> field = MutableStateFlow<Set<String>>(emptySet())

    private val loginFlow = store.data.map { it.login }.distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    val messages: Flow<PagingData<InboxMessage>> =
        loginFlow.flatMapLatest { login ->
            if (login == null) return@flatMapLatest emptyFlow()
            pagesFor(login, "")
        }.cachedIn(viewModelScope)

    private val draft = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    private val settledQuery: Flow<String> =
        draft.debounce(300.milliseconds)
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinctUntilChanged()

    fun onQueryChange(raw: String) {
        draft.value = raw
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchMessages: Flow<PagingData<InboxMessage>> =
        loginFlow.flatMapLatest { login ->
                if (login == null) return@flatMapLatest emptyFlow()
            settledQuery.flatMapLatest { query ->
                pagesFor(login, query)
            }
        }.cachedIn(viewModelScope)

    private fun pagesFor(
        login: LoginData,
        query: String
    ): Flow<PagingData<InboxMessage>> = Pager(PagingConfig(ApiConfig.INBOX_PAGE_SIZE, prefetchDistance = 205)) {
        MessagesPagingSource(api, login.cookie(), 0, query, logout = {
            viewModelScope.launch {
                store.updateData { it.copy(login = null) }
            }
        })
    }.flow

    fun markRead(messageId: String) = locallyRead.update { it + messageId }
}