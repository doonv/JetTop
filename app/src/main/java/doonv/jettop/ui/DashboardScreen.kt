package doonv.jettop.ui

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import doonv.jettop.R
import doonv.jettop.data.Cached
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.DisciplineEvents
import doonv.jettop.data.HourData
import doonv.jettop.data.Lesson
import doonv.jettop.ui.components.ExpressivePullToRefreshBox
import doonv.jettop.ui.components.uiStateContent
import doonv.jettop.ui.studentcard.DisciplineEventsCard
import doonv.jettop.ui.studentcard.DisciplineEventsViewModel
import doonv.jettop.ui.theme.GoogleSans
import doonv.jettop.ui.theme.GoogleSansHighlighted
import doonv.jettop.ui.theme.LessonColors
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    vm: DashboardViewModel,
    eventsVm: DisciplineEventsViewModel,
    firstName: String,
) {
    val scheduleState by vm.state.collectAsStateWithLifecycle()
    val eventsState by eventsVm.state.collectAsStateWithLifecycle()

    ExpressivePullToRefreshBox(
        isRefreshing = scheduleState.isRefreshing || eventsState.isRefreshing,
        onRefresh = {
            vm.refresh()
            eventsVm.refresh()
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Dashboard(
                scheduleState = scheduleState,
                eventsState = eventsState,
                firstName = firstName,
                onRetrySchedule = vm::refresh,
                onRetryEvents = eventsVm::refresh,
            )
        }
    }
}

@Composable
private fun Dashboard(
    scheduleState: UiState<Cached<List<DaySchedule>>>,
    eventsState: UiState<DisciplineEvents>,
    firstName: String,
    onRetrySchedule: () -> Unit,
    onRetryEvents: () -> Unit,
) {
    val schedule = (scheduleState as? UiState.Success)?.data
    val days = schedule?.data.orEmpty()
    val lastUpdated = schedule?.updatedAt
    val pagerState = rememberPagerState(
        pageCount = { days.size }, initialPage = when (LocalDate.now().dayOfWeek) {
            DayOfWeek.SUNDAY -> 0
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 0
        }
    )
    val scope = rememberCoroutineScope()
    val colors = remember(days) { LessonColors.build(days) }
    val hideZeroHour = remember(days) {
        days.all { day ->
            day.hoursData.none {
                it.hour == 0 && (!it.isEmpty || !it.hourName.isNullOrBlank())
            }
        }
    }

    val maxHeight = remember(days, hideZeroHour) {
        days.maxOfOrNull { day ->
            val hours = day.visibleHours(hideZeroHour)
            val emptyRows = hours.count { it.isEmpty }
            val lessonRows = hours.sumOf { if (it.isEmpty) 0 else it.schedule.size } + emptyRows
            lessonRows * (46.dp + 12.dp) +
                    emptyRows * (47.dp - 12.dp) +
                    (hours.size - 1).coerceAtLeast(0) * 1.dp
        } ?: 0.dp
    }
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(Duration.ofMinutes(1))
            now = System.currentTimeMillis()
        }
    }
    val justNow = stringResource(R.string.just_now)
    val rel = remember(lastUpdated, now) {
        if (lastUpdated == null) ""
        else if (now - lastUpdated < 60_000L) justNow
        else DateUtils.getRelativeTimeSpanString(lastUpdated, now, DateUtils.MINUTE_IN_MILLIS)
            .toString()
    }
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Column(Modifier.padding(8.dp), Arrangement.spacedBy(4.dp)) {
                Text(
                    stringResource(R.string.greeting_morning, firstName),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 16.dp)
                )
            }
        }
        uiStateContent(eventsState, onRetryEvents) { events ->
            item {
                Box(Modifier.padding(horizontal = 8.dp)) {
                    DashboardDisciplineCard(events)
                }
            }
        }
        uiStateContent(scheduleState, onRetrySchedule) {
            if (days.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(stringResource(R.string.no_data))
                    }
                }
                return@uiStateContent
            }
            item {
                Text(
                    stringResource(R.string.last_updated, rel),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(8.dp),
                )
            }
            stickyHeader {
                PrimaryTabRow(
                    selectedTabIndex = pagerState.currentPage
                ) {
                    val dayNames = stringArrayResource(R.array.days)
                    days.forEachIndexed { i, d ->
                        val today = d.dayIndex - 1 == LocalDate.now().dayOfWeek.value % 7
                        Tab(
                            selected = i == pagerState.currentPage,
                            onClick = { scope.launch { pagerState.animateScrollToPage(i) } },
                            // icon is used instead of text because text has a forced padding which cuts
                            // off weekday names in English
                            icon = {
                                Text(
                                    text = dayNames[d.dayIndex - 1],
                                    maxLines = 1,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        textAlign = TextAlign.Center,
                                        fontWeight = if (today) FontWeight.Bold else FontWeight.Medium,
                                    ),
                                    fontFamily = if (today) GoogleSansHighlighted else GoogleSans,
                                    softWrap = false
                                )
                            }
                        )
                    }
                }
            }
            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = maxHeight),
                    verticalAlignment = Alignment.Top
                ) { page ->
                    val day = days.getOrNull(page) ?: return@HorizontalPager
                    val hours = day.visibleHours(hideZeroHour)
                    DayPage(hours, colors)
                }
            }
        }
    }
}

@Composable
private fun DayPage(hours: List<HourData>, colors: Map<String, Color>) {
    if (hours.isEmpty()) {
        Column(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.no_lessons))
            }
        }
        return
    }
    DayHoursList(hours, colors)
}

@Composable
private fun DayHoursList(hours: List<HourData>, colors: Map<String, Color>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
    ) {
        hours.forEachIndexed { index, h ->
            @Composable
            fun RowScope.HourText() = Text(
                // TODO: figure out a flexible way to do this
                text = h.hourName?.dropWhile { it.isDigit() }?.trim() ?: "${h.hour}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 8.dp)
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                if (h.isEmpty) {
                    Row(
                        Modifier.padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Spacer(Modifier.weight(2f))
                        HourText()
                    }
                } else {
                    h.schedule.forEach { lesson ->
                        Row(
                            Modifier.padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LessonCard(lesson, colors)
                            HourText()
                        }
                    }
                }
            }
            if (index != hours.lastIndex) HorizontalDivider()
        }
    }
}

@Composable
private fun DashboardDisciplineCard(events: DisciplineEvents) {
    val latest = remember(events) {
        events.disciplineEvents
            .sortedByDescending { it.eventDate }
            .take(3)
            .toList()
    }
    DisciplineEventsCard(null, latest)
}

@Composable
private fun RowScope.LessonCard(
    lesson: Lesson,
    colors: Map<String, Color>
) {
    val cancelled = lesson.changes.any { it.isClassCancel }
    val solid = colors[LessonColors.keyOf(lesson)] ?: Color.Red
    val bg = solid.copy(alpha = 0.2f)
    Box(
        Modifier
            .weight(2f)
            .let { if (cancelled) it.alpha(0.3f) else it }
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(bg)
            .rightBorder(4.dp, solid)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    lesson.subject ?: "-",
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                    textDecoration = if (cancelled) TextDecoration.LineThrough else null
                )

                lesson.subjectLevel?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        it,
                        fontSize = 11.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(solid.copy(alpha = 0.3f))
                            .padding(horizontal = 6.dp),
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    listOfNotNull(
                        lesson.teacherPrivateName, lesson.teacherLastName
                    ).joinToString(" ").ifBlank { "-" },
                    fontSize = 14.sp,
                    modifier = Modifier.alignByBaseline()
                )
                lesson.room?.let {
                    Text(
                        it,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.7f)
                    )
                }
            }
        }
    }
}

fun Modifier.rightBorder(width: Dp, color: Color) = drawBehind {
    val stroke = width.toPx()
    val x = size.width - stroke / 2
    drawLine(
        color, Offset(x, 0f), Offset(
            x, size.height
        ), stroke
    )
}

private fun DaySchedule.visibleHours(hideZeroHour: Boolean): List<HourData> =
    hoursData
        .filterNot { hideZeroHour && it.hour == 0 }
        .dropLastWhile { it.isEmpty }
