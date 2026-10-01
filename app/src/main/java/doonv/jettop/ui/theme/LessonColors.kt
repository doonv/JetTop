package doonv.jettop.ui.theme

import androidx.compose.ui.graphics.Color
import doonv.jettop.data.DaySchedule
import doonv.jettop.data.Lesson
import doonv.jettop.ui.theme.GematriaUtils.LETTERS
import doonv.jettop.ui.theme.GematriaUtils.VALUES

/**
 * Port of Webtop's `SchedualeViewComponent` coloring
 * (`main.*.js`: colors palette + gemCalc + scheduale setter)
 */
object LessonColors {
    private val PALETTE = listOf(
        "3c4db7", "6733b9", "9c1ab2", "eb1461", "f6402b",
        "1193f5", "01a6f6", "00bbd6", "009788", "46af4a",
        "ff9801", "ffc100", "ffec16", "ccdd1d", "88c440",
        "ff5505", "7b5548", "9d9d9d", "5f7c8c", "000000"
    )

    fun gemCalc(s: String): Int {
        var sum = 0
        for (ch in s) {
            val i = LETTERS.indexOf(ch)
            if (i >= 0) sum += VALUES[i]
            else if (ch in '0'..'9') sum += ch - '0'
        }
        return sum
    }

    fun keyOf(lesson: Lesson): String =
        lesson.studyGroupID?.toString() ?: lesson.subject ?: "null"

    private fun hashInput(lesson: Lesson): String =
        "${lesson.subject}${lesson.studyGroupID?.toString() ?: lesson.subject}" +
                "${lesson.classes}${lesson.subjectLevel}" +
                "${lesson.teacherLastName}${lesson.teacherPrivateName}"

    fun hexToColor(hex: String): Color {
        val v = hex.removePrefix("#")
        return Color(
            red = v.substring(0, 2).toInt(16),
            green = v.substring(2, 4).toInt(16),
            blue = v.substring(4, 6).toInt(16)
        )
    }

    fun indexFor(lesson: Lesson): Int {
        val r = gemCalc(hashInput(lesson))
        return (r % 29 + r / 13) % (PALETTE.size - 1)
    }

    fun build(days: List<DaySchedule>): Map<String, Color> {
        val map = LinkedHashMap<String, Color>()
        for (day in days) {
            for (hour in day.hoursData) {
                for (lesson in hour.schedule) {
                    val idKey = lesson.studyGroupID?.toString()
                    if ((idKey != null && idKey in map) ||
                        (lesson.subject != null && lesson.subject in map)
                    ) continue
                    val hex = PALETTE[indexFor(lesson)]
                    val color = hexToColor(hex)
                    map[keyOf(lesson)] = color
                }
            }
        }
        return map
    }
}
