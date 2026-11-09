package doonv.jettop.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DotSeparatedText(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    Text(
        text = items
            .filter { it.isNotBlank() }
            .joinToString(" · "),
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
