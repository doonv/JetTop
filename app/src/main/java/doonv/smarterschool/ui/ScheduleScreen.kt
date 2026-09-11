package doonv.smarterschool.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
import doonv.smarterschool.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import doonv.smarterschool.data.DaySchedule
import doonv.smarterschool.ui.theme.LessonColors

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    vm: ScheduleViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            is ScheduleUiState.Loading -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
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
    var selected by remember { mutableIntStateOf(0) }
    val day = days.getOrNull(selected) ?: days.first()
    val colors = remember(days) { LessonColors.build(days) }
    Column(Modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(selectedTabIndex = selected) {
            val dayNames = stringArrayResource(R.array.days)
            days.forEachIndexed { i, d ->
                Tab(
                    selected = i == selected,
                    onClick = { selected = i },
                    text = { Text(dayNames[d.dayIndex - 1]) }
                )
            }
        }
        val hours =
            day.hoursData.filter { !it.hourName.isNullOrBlank() && it.schedule.isNotEmpty() }
        if (hours.isEmpty()) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.no_lessons))
            }
            return
        }
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                Row(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        stringResource(R.string.hour),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        stringResource(R.string.subject),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                }
                HorizontalDivider()
            }
            items(hours, key = { it.hour }) { h ->
                h.schedule.forEach { lesson ->
                    val cancelled = lesson.changes.any { it.isClassCancel }
                    val solid = colors[LessonColors.keyOf(lesson)] ?: Color.Red
                    val bg = solid.copy(alpha = 0.2f)
                    Row(Modifier.padding(vertical = 6.dp)) {
                        Text(
                            text = h.hourName?.substringAfter("       ") ?: "${h.hour}",
                            Modifier.weight(1f)
                        )
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Box(
                                Modifier
                                    .weight(2f)
                                    .let { if (cancelled) it.alpha(0.3f) else it }
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(bg)
                                    .rightBorder(3.dp, solid)
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            ) {
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
                                                lesson.teacherPrivateName,
                                                lesson.teacherLastName
                                            ).joinToString(" ").ifBlank { "-" },
                                            fontSize = 14.sp
                                        )
                                        lesson.room?.let { Text(it, fontSize = 14.sp) }
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
}

fun Modifier.rightBorder(width: Dp, color: Color) =
    drawBehind {
        val stroke = width.toPx()
        val x = size.width - stroke / 2
        drawLine(
            color, Offset(x, 0f), Offset(
                x,
                size.height
            ), stroke
        )
    }