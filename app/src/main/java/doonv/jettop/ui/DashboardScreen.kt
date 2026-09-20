package doonv.jettop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier, vm: ScheduleViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            is ScheduleUiState.Loading -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { LoadingIndicator() }

            is ScheduleUiState.Error -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.error_message, s.message))
                Button(onClick = { vm.refresh() }, modifier = Modifier.padding(top = 8.dp)) {
                    Text(stringResource(R.string.retry))
                }
            }

            is ScheduleUiState.Success -> DayTabs(s.days)
        }
    }
}

@Composable
private fun DayTabs(days: List<DaySchedule>) {
    if (days.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_data))
        }
        return
    }
    val pagerState = rememberPagerState(pageCount = { days.size })
    val scope = rememberCoroutineScope()
    val colors = remember(days) { LessonColors.build(days) }
    val maxHeight = remember(days) {
        days.maxOf {
            val hours = it.hoursData.dropLastWhile { h -> h.schedule.isEmpty() }
            hours
                .sumOf { h -> (if (h.schedule.isEmpty()) 36 else h.schedule.size * 56) + 13 }.dp + hours.size * 2.dp
        } + 2.dp
    }
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Column(Modifier.padding(8.dp)) {
                Text(
                    "לילה טוב, {שם}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                EventsSummaryCard()
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
                        text = { Text(dayNames[d.dayIndex - 1]) })
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
                DayPage(day, colors)
            }
        }
    }
}

@Composable
private fun DayPage(day: DaySchedule, colors: Map<String, Color>) {
    val hours = day.hoursData.dropLastWhile { it.schedule.isEmpty() }
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
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                if (h.schedule.isEmpty()) {
                    Row(
                        Modifier.padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.window),
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier
                                .weight(2f)
                                .padding(6.dp)
                                .alpha(0.2f)
                        )
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
        Column(verticalArrangement = Arrangement.spacedBy(-20.dp)) {
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
            .padding(horizontal = 16.dp, vertical = 4.dp)) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    lesson.subject ?: "-",
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (cancelled) TextDecoration.LineThrough else null
                )

                lesson.subjectLevel?.let {
                    Text(
                        it,
                        fontSize = 12.sp,
                        modifier = Modifier.alpha(0.7f)
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    listOfNotNull(
                        lesson.teacherPrivateName, lesson.teacherLastName
                    ).joinToString(" ").ifBlank { "-" }, fontSize = 14.sp
                )
                lesson.room?.let { Text(it, fontSize = 12.sp) }
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