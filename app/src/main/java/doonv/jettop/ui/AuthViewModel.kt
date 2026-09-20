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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

sealed interface AuthState {
    data object Checking : AuthState
    data class LoggedOut(val isLoading: Boolean = false, val error: String? = null)
    data class LoggedIn(val login: LoginData) : AuthState
}

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val store = app.applicationContext.dataStore
    private val _login = MutableStateFlow(AuthState.LoggedOut(false))
    val state = combine(store.data, _login) { stored,
                                              login ->
        if (!stored.login?.token.isNullOrBlank())
            AuthState.LoggedIn(stored.login)
        else login
    }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(
                5000
            ), AuthState.Checking
        )

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _login.value = AuthState.LoggedOut(isLoading = true)
            try {
                val resp = ApiClient.api.login(
                    // They probably don't check the counter
                    LoginRequest.forLogin(username, password, 0)
                )
                val data = resp.data
                if (resp.status && data != null && data.token.isNotBlank()
                    && data.institutionCode != 0 && data.classCode.isNotBlank()
                ) {
                    store.updateData { it.copy(login = data) }
                    _login.value = AuthState.LoggedOut()
                } else {
                    _login.value = AuthState.LoggedOut(
                        error = resp.message ?: resp.errorDescription
                        ?: getApplication<Application>().getString(R.string.login_error_invalid)
                    )
                }
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