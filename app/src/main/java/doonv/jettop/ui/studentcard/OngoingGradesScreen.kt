package doonv.jettop.ui.studentcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import doonv.jettop.R
import doonv.jettop.data.Grade
import doonv.jettop.data.PupilCardDivision
import doonv.jettop.data.PupilCardSettings
import doonv.jettop.ui.components.DotSeparatedText
import doonv.jettop.ui.components.ExpressivePullToRefreshBox
import doonv.jettop.ui.components.UiStateContent
import doonv.jettop.ui.components.uiStateContent
import doonv.jettop.ui.rememberFullDateFormatter
import doonv.jettop.ui.theme.Symbols
import kotlinx.datetime.toJavaLocalDateTime
import java.text.DecimalFormat
import kotlin.random.Random

private val numFormatter = DecimalFormat("0.##")

@Composable
fun OngoingGradesScreen(
    modifier: Modifier = Modifier,
    vm: OngoingGradesViewModel,
    settingsVm: PupilCardSettingsViewModel,
    onBack: () -> Unit,
) {
    val gradesState by vm.sortedGrades.collectAsStateWithLifecycle()
    val settingsState by settingsVm.state.collectAsStateWithLifecycle()
    val divisions by vm.divisions.collectAsStateWithLifecycle()
    val sortOrder by vm.sortOrder.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.ongoing_grades)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Symbols.Outlined.ArrowBack24,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                actions = {
                    GradesSortButton(sortOrder) { vm.sortOrder.value = it }
                }
            )
        },
    ) { innerPadding ->
        ExpressivePullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = settingsState.isRefreshing || gradesState.isRefreshing,
            onRefresh = {
                settingsVm.refresh()
                vm.refresh()
            }
        ) {
            UiStateContent(
                state = settingsState,
                onRetry = settingsVm::refresh
            ) { settings ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        GradeChips(vm, settings, divisions)
                    }

                    uiStateContent(
                        state = gradesState,
                        onRetry = vm::refresh
                    ) { grades ->
                        if (grades.isEmpty()) {
                            item { Text(stringResource(R.string.no_data)) }
                        }
                        items(grades) { grade ->
                            GradeCard(grade)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun GradeChips(
    vm: OngoingGradesViewModel,
    settings: PupilCardSettings,
    divisions: List<PupilCardDivision>
) {
    val selectedDivisionId by vm.selectedDivisionId.collectAsStateWithLifecycle()
    val selectedYear by vm.selectedYear.collectAsStateWithLifecycle()
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        divisions.forEach { division ->
            val selected = selectedDivisionId == division.divisionId

            FilterChip(
                selected = selected,
                onClick = {
                    vm.selectedDivisionId.value = division.divisionId
                },
                label = {
                    Text(
                        division.divisionName
                    )
                },
                leadingIcon = if (selected) {
                    {
                        Icon(
                            painter = rememberVectorPainter(Symbols.Outlined.Check20),
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        val years =
            (settings.currentStudyYear - settings.numBackYearsAllowed)..settings.currentStudyYear
        items(years.reversed().toList()) { year ->
            val selected = selectedYear == year

            FilterChip(
                selected = selected,
                onClick = {
                    vm.selectedYear.value = year
                },
                label = {
                    Text(year.toString())
                },
                leadingIcon = if (selected) {
                    {
                        Icon(
                            painter = rememberVectorPainter(Symbols.Outlined.Check20),
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@Composable
private fun GradeCard(grade: Grade) {
    val dateFormatter = rememberFullDateFormatter()

    Card {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GradeBadge(grade)
            Column {
                Text(
                    grade.title.orEmpty(),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                DotSeparatedText(
                    listOf(
                        grade.subject
                            ?: stringResource(R.string.message_no_subject),
                        "${grade.teacherFirstName} ${grade.teacherLastName}"
                    )
                )
                DotSeparatedText(
                    listOfNotNull(
                        grade.date?.toJavaLocalDateTime()
                            ?.format(dateFormatter),
                        grade.weight?.let { "${numFormatter.format(it)}%" }
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun GradeBadge(
    grade: Grade,
    modifier: Modifier = Modifier
) {
    val shape = listOf(
        MaterialShapes.Sunny,
        MaterialShapes.Cookie4Sided,
        MaterialShapes.Cookie6Sided,
        MaterialShapes.Cookie7Sided,
        MaterialShapes.Cookie9Sided,
        MaterialShapes.Cookie12Sided,
        MaterialShapes.Clover4Leaf,
        MaterialShapes.Clover8Leaf
    ).random(Random(grade.subject.hashCode())).toShape()
    val colors = MaterialTheme.colorScheme
    val palettes = listOf(
        colors.primary to colors.onPrimary,
        colors.primaryContainer to colors.onPrimaryContainer,
        colors.primaryFixed to colors.onPrimaryFixed,

        colors.secondary to colors.onSecondary,
        colors.secondaryFixed to colors.onSecondaryFixed,

        colors.tertiary to colors.onTertiary,
        colors.tertiaryContainer to colors.onTertiaryContainer,
        colors.tertiaryFixed to colors.onTertiaryFixed,
    )
    val (background, foreground) = if (grade.grade == null)
        colors.secondaryContainer to colors.onSecondaryContainer
    else palettes.random(Random(grade.grade.hashCode()))

    Box(
        modifier = modifier
            .size(64.dp)
            .background(
                color = background,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = grade.grade?.let { numFormatter.format(it) } ?: "—", // spooky
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = foreground,
            maxLines = 1
        )
    }
}

@Composable
fun GradesSortButton(
    selectedSort: GradeSortOrder,
    onSortSelected: (GradeSortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Symbols.Outlined.Sort24,
                contentDescription = stringResource(R.string.sort)
            )
        }

        DropdownMenuPopup(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuGroup(
                shapes = MenuDefaults.groupShape(0, 1)
            ) {
                MenuDefaults.DropdownMenuGroupLabel {
                    Text(stringResource(R.string.sort_by))
                }

                GradeSortOrder.entries.forEachIndexed { index, option ->
                    SelectableDropdownMenuItem(
                        selected = selectedSort == option,
                        onClick = {
                            onSortSelected(option)
                            expanded = false
                        },
                        text = { Text(stringResource(option.resource)) },
                        shapes = MenuDefaults.itemShape(
                            index, GradeSortOrder.entries.size
                        ),
                        selectedLeadingIcon = {
                            Icon(
                                Symbols.Outlined.Check20,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}
