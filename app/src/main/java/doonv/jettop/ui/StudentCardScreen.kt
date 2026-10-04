package doonv.jettop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import doonv.jettop.R
import doonv.jettop.data.LoginData
import doonv.jettop.ui.theme.Symbols
import doonv.jettop.ui.theme.GematriaUtils


@Composable
fun StudentCardScreen(
    modifier: Modifier = Modifier,
    loginData: LoginData
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${loginData.firstName} ${loginData.lastName}",
                style = MaterialTheme.typography.headlineLargeEmphasized
            )
            IconButton(onClick = {}) {
                Icon(
                    Symbols.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        }
        Text(
            listOfNotNull(
                loginData.classCode.toIntOrNull()?.let { GematriaUtils.fromNum(it) }
                    ?: loginData.classCode,
                loginData.classNumber?.toString()
            ).joinToString(" "),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        MenuGrid(
            listOf(
                MenuTile(R.string.lesson_events, Symbols.Filled.EventNote) {},
                MenuTile(R.string.non_lesson_events, Symbols.Filled.Campaign) {},
                MenuTile(R.string.accommodations, Symbols.Filled.Psychology) {},
                MenuTile(R.string.private_lessons, Symbols.Filled.Group) {},
                MenuTile(R.string.submission_and_exam_grades, Symbols.Filled.AssignmentTurnedIn) {},
                MenuTile(R.string.ongoing_grades, Symbols.Filled.Grading) {},
            )
        )
    }
}

private const val GridCols = 2

private data class MenuTile(
    val label: Int,              // string resource
    val icon: ImageVector,
    val onClick: () -> Unit,
)

@Composable
private fun MenuGrid(
    tiles: List<MenuTile>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.clip(MaterialTheme.shapes.extraLarge),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tiles.chunked(GridCols).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { tile ->
                    FilledTonalButton(
                        onClick = tile.onClick,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        contentPadding = PaddingValues(4.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        TileContent(tile)
                    }
                }
                // keeps a 7-item list from stretching the last button across the row
                repeat(GridCols - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun TileContent(tile: MenuTile) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            tile.icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = stringResource(tile.label),
            style = MaterialTheme.typography.labelMediumEmphasized,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(4.dp),
        )
    }
}
