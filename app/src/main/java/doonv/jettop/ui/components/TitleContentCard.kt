package doonv.jettop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TitleContentCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val rounded = MaterialTheme.shapes.medium
    val base = MaterialTheme.shapes.extraSmall
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = rounded.topStart,
                topEnd = rounded.topEnd,
                bottomEnd = base.bottomEnd,
                bottomStart = base.bottomStart
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMediumEmphasized.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = base.topStart,
                topEnd = base.topEnd,
                bottomEnd = rounded.bottomEnd,
                bottomStart = rounded.bottomStart
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(Modifier.padding(8.dp)) {
                content()
            }
        }
    }
}
