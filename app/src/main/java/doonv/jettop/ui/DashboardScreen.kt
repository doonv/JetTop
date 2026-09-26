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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import doonv.jettop.R
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.HourData
import doonv.jettop.data.Lesson
import doonv.jettop.ui.theme.LessonColors
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier, vm: ScheduleViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    PullToRefreshBox(isRefreshing = state.refreshing(), onRefresh = { vm.refresh() }) {
        Column(modifier = modifier.fillMaxSize()) {
            when (val s = state) {
                is ScheduleUiState.Loading -> Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { LoadingIndicator() }

                is ScheduleUiState.Error -> Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(Modifier.fillMaxWidth(0.5f)) {
                        Text(
                            stringResource(R.string.error_message),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(s.message, color = MaterialTheme.colorScheme.error)
                        Button(
                            onClick = { vm.refresh() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }

                is ScheduleUiState.Success -> Dashboard(s.days, s.firstName, s.lastUpdated)
            }
        }
    }
}

@Composable
private fun Dashboard(
    days: List<DaySchedule>,
    firstName: String,
    lastUpdated: Long
) {
    if (days.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_data))
        }
        return
    }
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
        days.maxOf { day ->
            val hours = day.visibleHours(hideZeroHour)
            val emptyRows = hours.count { it.isEmpty }
            val lessonRows = hours.sumOf { if (it.isEmpty) 0 else it.schedule.size } + emptyRows
            lessonRows * (46.dp + 12.dp) +
                    emptyRows * (47.dp - 12.dp) +
                    (hours.size - 1).coerceAtLeast(0) * 1.dp
        }
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
        if (now - lastUpdated < 60_000L) justNow
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
                EventsSummaryCard()
                Text(
                    stringResource(R.string.last_updated, rel),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        stickyHeader {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                val dayNames = stringArrayResource(R.array.days)
                days.forEachIndexed { i, d ->
                    Tab(
                        selected = i == pagerState.currentPage,
                        onClick = { scope.launch { pagerState.animateScrollToPage(i) } },
                        text = {
                            Text(
                                text = dayNames[d.dayIndex - 1],
                                maxLines = 1,
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
private fun EventsSummaryCard() {
    Card(Modifier.fillMaxWidth()) {
        Text(
            "אירועים בשיעור",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider()
        Column(verticalArrangement = Arrangement.spacedBy((-20).dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    "איחור",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "123",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    "אנגלית",
                    fontSize = 12.sp
                )
                Text(
                    "8:45 - 8:00",
                    fontSize = 12.sp
                )
            }
        }
    }
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
                    overflow = TextOverflow.Clip,
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