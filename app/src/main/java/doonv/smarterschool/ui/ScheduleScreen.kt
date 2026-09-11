package doonv.smarterschool.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.lifecycle.viewmodel.compose.viewModel
import doonv.smarterschool.R
import doonv.smarterschool.data.DaySchedule
import doonv.smarterschool.data.HourData
import doonv.smarterschool.ui.theme.LessonColors
import kotlinx.coroutines.launch

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier, vm: ScheduleViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            is ScheduleUiState.Loading -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

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
    Column(Modifier.fillMaxSize()) {
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
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            val day = days.getOrNull(page) ?: return@HorizontalPager
            DayPage(day, colors)
        }
    }
}

@Composable
private fun DayPage(day: DaySchedule, colors: Map<String, Color>) {
    val hours = day.hoursData.dropLastWhile { it.schedule.isEmpty() }
    if (hours.isEmpty()) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.no_lessons))
        }
        return
    }
    DayHoursList(hours, colors)
}

@Composable
private fun DayHoursList(hours: List<HourData>, colors: Map<String, Color>) {
    LazyColumn(
        Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(hours, key = { it.hour }) { h ->
            @Composable
            fun RowScope.HourText() = Text(
                text = h.hourName?.substringAfter("       ") ?: "${h.hour}",
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
            )

            if (h.schedule.isEmpty()) {
                Row(
                    Modifier.padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HourText()
                    Text(
                        text = stringResource(R.string.window),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .weight(2f)
                            .padding(6.dp)
                            .alpha(0.2f)
                    )
                }
            } else {
                h.schedule.forEach { lesson ->
                    val cancelled = lesson.changes.any { it.isClassCancel }
                    val solid = colors[LessonColors.keyOf(lesson)] ?: Color.Red
                    val bg = solid.copy(alpha = 0.2f)
                    Row(
                        Modifier.padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HourText()
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Box(
                                Modifier
                                    .weight(2f)
                                    .let { if (cancelled) it.alpha(0.3f) else it }
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(bg)
                                    .rightBorder(3.dp, solid)
                                    .padding(horizontal = 16.dp, vertical = 4.dp)) {
                                Column {
                                    Text(
                                        lesson.subject ?: "-",
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = if (cancelled) TextDecoration.LineThrough else null
                                    )
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            listOfNotNull(
                                                lesson.teacherPrivateName, lesson.teacherLastName
                                            ).joinToString(" ").ifBlank { "-" }, fontSize = 14.sp
                                        )
                                        lesson.room?.let { Text(it, fontSize = 14.sp) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HorizontalDivider()
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