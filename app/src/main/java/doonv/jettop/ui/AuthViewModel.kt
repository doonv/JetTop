package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.R
import doonv.jettop.data.ApiClient
import doonv.jettop.data.LoginData
import doonv.jettop.data.LoginRequest
import doonv.jettop.data.dataStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException

sealed interface AuthState {
    data class LoggedOut(val isLoading: Boolean = false, val error: String? = null) : AuthState
    data class LoggedIn(val login: LoginData) : AuthState
}

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _login = MutableStateFlow(AuthState.LoggedOut(false))

    // Blocking here because fetching from data stores is extremely fast
    // but not blocking causes a flicker. And it requires more state
    private val storedLogin = runBlocking { store.data.first().login }

    val state = combine(store.data, _login) { stored,
                                              login ->
        if (!stored.login?.token.isNullOrBlank())
            AuthState.LoggedIn(stored.login)
        else login
    }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            if (!storedLogin?.token.isNullOrBlank()) AuthState.LoggedIn(storedLogin)
            else AuthState.LoggedOut()
        )

    @OptIn(ExperimentalSerializationApi::class)
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _login.value = AuthState.LoggedOut(isLoading = true)
            try {
                val resp = ApiClient.api.login(
                    // They probably don't check the counter
                    LoginRequest.forLogin(username, password, 0)
                )
                val data = resp.data
                if (resp.status && data.token.isNotBlank() && data.institutionCode != 0) {
                    store.updateData { it.copy(login = data) }
                    _login.value = AuthState.LoggedOut()
                } else {
                    _login.value = AuthState.LoggedOut(
                        error = resp.message ?: resp.errorDescription
                        ?: getApplication<Application>().getString(R.string.login_error_invalid)
                    )
                }
            } catch (e: MissingFieldException) {
                _login.value = AuthState.LoggedOut(
                    error = getApplication<Application>()
                        .getString(R.string.login_error_missing_details)
                )
            } catch (e: SerializationException) {
                _login.value = AuthState.LoggedOut(
                    error = getApplication<Application>().getString(R.string.login_error_invalid)
                )
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _login.value = AuthState.LoggedOut(
                    error = getApplication<Application>().getString(R.string.login_error_network)
                )
            }
        }
    }
}