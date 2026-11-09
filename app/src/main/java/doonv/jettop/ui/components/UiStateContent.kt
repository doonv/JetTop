package doonv.jettop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import doonv.jettop.R
import doonv.jettop.ui.UiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> UiStateContent(
    state: UiState<T>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            UiState.Loading -> LoadingIndicator()
            is UiState.Error -> RequestError(
                message = state.error.localizedMessage
                    ?: stringResource(R.string.unknown_error),
                onRetry = onRetry,
                modifier = Modifier.fillMaxWidth(0.8f).padding(16.dp),
            )
            is UiState.Success -> Column(Modifier.fillMaxSize()) {
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    content(state.data)
                }
            }
        }
    }
}

@Composable
private fun RequestError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.error_message),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error,
        )
        Text(message, color = MaterialTheme.colorScheme.error)
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}
