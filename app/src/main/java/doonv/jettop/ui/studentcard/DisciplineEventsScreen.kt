package doonv.jettop.ui.studentcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import doonv.jettop.R
import doonv.jettop.data.DisciplineEvent
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.ui.components.TitleContentCard
import doonv.jettop.ui.theme.Symbols
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DisciplineEventsScreen(
    modifier: Modifier = Modifier,
    vm: DisciplineEventsViewModel,
    onBack: () -> Unit,
) {
    val state by vm.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.discipline_events)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Symbols.Outlined.ArrowBack24,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Symbols.Outlined.Sort24,
                            contentDescription = stringResource(R.string.sort)
                        )
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Symbols.Filled.Gavel24,
                            contentDescription = stringResource(R.string.justify_event)
                        )
                    }
                }
            )
        },
        floatingActionButton = { EventsScreenFab() }
    ) { innerPadding ->
        when (val s = state) {
            is DisciplineEventsUiState.Loading -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { LoadingIndicator() }

            is DisciplineEventsUiState.Error -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(Modifier.fillMaxWidth(0.8f)) {
                    Text(
                        stringResource(R.string.error_message),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(s.message, color = MaterialTheme.colorScheme.error)
                    Button(
                        onClick = { vm.load() },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }

            is DisciplineEventsUiState.Success -> DisciplineEventsSuccess(
                innerPadding,
                s.events
            )
        }
    }
}

@Composable
private fun DisciplineEventsSuccess(
    innerPadding: PaddingValues,
    events: DisciplineEvents,
) {
    val grouped = remember(events) {
        events.disciplineEvents
            .sortedByDescending { it.eventDate }
            .groupBy { it.eventDate.date }
            .toList()
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(grouped) { (date, events) ->
            DisciplineEventsCard(date, events)
        }

        item {
            Spacer(Modifier.size(1.dp, 800.dp))
        }
    }
}

@Composable
fun DisciplineEventsCard(
    date: LocalDate?,
    events: List<DisciplineEvent>
) {
    val formatter =
        DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", LocalLocale.current.platformLocale)
    TitleContentCard(title = date?.toJavaLocalDate()?.format(formatter) ?: "Class Events") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            events.forEachIndexed { index, event ->
                if (index != 0)
                    HorizontalDivider()
                Column {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Row(Modifier.alignByBaseline(), Arrangement.spacedBy(4.dp)) {
                            Text(
                                event.eventType,
                                textDecoration = if (event.justified) TextDecoration.LineThrough else null,
                                modifier = Modifier.alignByBaseline(),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMediumEmphasized.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            if (event.justified) {
                                Icon(
                                    Symbols.Filled.GavelOnly20,
                                    contentDescription = stringResource(R.string.event_justified),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .size(with(LocalDensity.current) { MaterialTheme.typography.titleMediumEmphasized.fontSize.toDp() })
                                        .alignBy { (it.measuredHeight * 0.8f).toInt() }

                                )
                            }
                        }
                        Text(
                            event.hourName,
                            modifier = Modifier.alignByBaseline(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text(
                            event.subjectName,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            if (date == null)
                                event.teacherName.orEmpty()
                            else
                                event.eventDate.toJavaLocalDateTime().format(formatter),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    if (!event.justifiedRemark.isNullOrEmpty()) {
                        Text(
                            stringResource(
                                R.string.justification_notes,
                                event.justifiedRemark
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EventsScreenFab() {
    val label = stringResource(R.string.add_justification)
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            TooltipAnchorPosition.Above
        ),
        tooltip = {
            PlainTooltip {
                Text(label)
            }
        },
        state = rememberTooltipState(),
    ) {
        FloatingActionButton(onClick = { /* TODO */ }) {
            Icon(Symbols.Filled.GavelPlus24, label)
        }
    }
}
