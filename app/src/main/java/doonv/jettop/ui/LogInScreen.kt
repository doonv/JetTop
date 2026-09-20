package doonv.jettop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import doonv.jettop.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, isLoading: Boolean, error: String?) {
    val host = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val resetUnimplemented = stringResource(R.string.login_reset_unimplemented)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(host) }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(
                stringResource(R.string.login_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (error != null) {
                Text(error, color = MaterialTheme.colorScheme.error)
            }
            val textStyle = LocalTextStyle.current.copy(textDirection = TextDirection.Ltr)
            val usernameState = rememberTextFieldState()
            val noWhitespace = remember {
                InputTransformation {
                    if (asCharSequence().any { it.isWhitespace() }) revertAllChanges()
                }
            }
            TextField(
                state = usernameState,
                label = { Text(stringResource(R.string.login_username)) },
                modifier = Modifier.semantics { contentType = ContentType.Username },
                textStyle = textStyle,
                enabled = !isLoading,
                inputTransformation = noWhitespace
            )
            val passwordState = rememberTextFieldState()
            SecureTextField(
                state = passwordState,
                label = { Text(stringResource(R.string.login_password)) },
                modifier = Modifier.semantics { contentType = ContentType.Password },
                textStyle = textStyle,
                enabled = !isLoading
            )
            Text(
                stringResource(R.string.login_forgot_password),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .alpha(if (isLoading) 0.5f else 1f)
                    .clickable(enabled = !isLoading) {
                        scope.launch { host.showSnackbar(resetUnimplemented) }
                    })

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                @OptIn(ExperimentalMaterial3ExpressiveApi::class)
                when (isLoading) {
                    true -> LoadingIndicator()
                    false -> Button(
                        onClick = {
                            onLogin(
                                usernameState.text.toString(),
                                passwordState.text.toString()
                            )
                        },
                        enabled = usernameState.text.isNotBlank() && passwordState.text.isNotBlank()
                    ) {
                        Text(stringResource(R.string.login_button))
                    }
                }
            }

        }
    }
}
