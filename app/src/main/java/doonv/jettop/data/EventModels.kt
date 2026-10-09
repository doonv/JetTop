package doonv.jettop.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class LessonEventsRequest(
//   val weekIndex: Int,
//   val viewType: Int,
//   val studyYear: Int,
//   val studyYearName: String,
    val studentID: String,
//   val studentName: String,
//   val classCode: Int,
//   val periodID: Int,
//   val periodName: String,
    val moduleID: Int = PupilCardModule.LESSON_EVENTS.id
)

@Serializable
data class LessonEvents(
    val viewRemarkForParentPupil: Boolean? = null,
    val canRequestJustification: Boolean? = null,
    val behaviorGrade: JsonElement? = null,
    val studentshipGrade: JsonElement? = null,
    val canDelete: Boolean? = null,
    val hours: List<EventHour>? = null,
    val eventsTypes: List<EventType>? = null,
    val diciplineEvents: List<DisciplineEvent>? = null,
    val groups: JsonElement? = null,
)

@Serializable
data class EventHour(
    val hour: Int? = null,
    val name: String? = null,
    val fromTime: String? = null,
    val toTime: String? = null,
)

@Serializable
data class EventType(
    val id: Int? = null,
    val name: String? = null,
    // Mixing cases in the SAME type. I can't.
    @SerialName("enable_justification_request") val enableJustificationRequest: Int? = null,
    val isPublic: Int? = null,
    @SerialName("type_id") val typeId: Int? = null,
    @SerialName("whole_day_fill_enabled") val wholeDayFillEnabled: Int? = null,
    val reportDisplay: Int? = null,
    @SerialName("points_studentship") val pointsStudentship: Int? = null,
    val points: Int? = null,
    val enableJustification: Boolean? = null,
    val viewOrder: Int? = null,
    val automaticMessageSend: Int? = null,
)

@Serializable
data class DisciplineEvent(
    @SerialName("eventID") val eventId: Int? = null,
    val eventType: String,
    val eventCode: Int? = null,
    val eventDate: LocalDateTime,
    val allowJustification: Boolean? = null,
    val enableJustified: Boolean? = null,
    val isJustified: Boolean? = null,
    val isRequestJustification: Boolean? = null,
    val justifiedReason: String? = null,
    val justificationStatus: Boolean? = null,
    @SerialName("justificationID") val justificationId: Int? = null,
    val notJustified: Boolean, // ????
    val subjectName: String,
    val subjectCode: Int? = null,
    val subjectLevel: String? = null,
    val teacherName: String? = null,
    val hourNum: Int? = null,
    val hourName: String,
    val remark: String? = null,
    val justifiedRemark: String? = null,
    @SerialName("studyGroupID") val studyGroupId: Int? = null,
    val periodId: Int? = null,
    val eventTypeId: Int? = null,
    val lessonNums: Int? = null,
    val autoJustifyInEvents: Boolean? = null,
    val idType: Int? = null,
) {
    val justified: Boolean get() = !notJustified
}