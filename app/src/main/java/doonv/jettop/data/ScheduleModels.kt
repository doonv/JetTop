package doonv.jettop.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRequest(
    val institutionCode: Int,
    val selectedValue: String,
    val typeView: Int
)

@Serializable
data class DaySchedule(
    val dayIndex: Int,
    val hoursData: List<HourData> = emptyList()
)

@Serializable
data class HourData(
    val hour: Int = 0,
    val hourName: String? = null,
    // Dumbass Smart School devs don't know how to spell
    @SerialName("scheduale") val schedule: List<Lesson> = emptyList(),
    val changes: List<LessonChange> = emptyList(),
    val events: List<EventItem> = emptyList(),
    val exams: List<ExamItem> = emptyList()
)

@Serializable
data class Lesson(
    val day: Int = 0,
    val hour: Int = 0,
    val roomID: Int? = null,
    val studyGroupID: Long? = null,
    val subject: String? = null,
    val subjectLevel: String? = null,
    val room: String? = null,
    val teacherPrivateName: String? = null,
    val teacherLastName: String? = null,
    val classes: String? = null,
    val changes: List<LessonChange> = emptyList()
)

@Serializable
data class LessonChange(
    val definition: String? = null,
    val type: String? = null,
    val isClassCancel: Boolean = false,
    val group: String? = null
)

@Serializable
data class EventItem(
    val definition: String? = null
)

@Serializable
data class ExamItem(
    val definition: String? = null
)
