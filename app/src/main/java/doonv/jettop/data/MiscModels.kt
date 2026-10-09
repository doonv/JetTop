package doonv.jettop.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val status: Boolean = false,
    val data: T,
    val message: String? = null,
    val errorId: String? = null,
    val errorDescription: String? = null,
    val errorHTML: String? = null
)

@Serializable
data class MenuCounters(
    val unreadMessages: Int = 0,
    val unreadNotifications: Int = 0,
    @SerialName("unreadPedegogicalNotifications")
    val unreadPedagogicalNotifications: Int = 0,
    val unreadSystemNotifications: Int = 0,
    val unreadPersonalNotifications: Int = 0,
)

enum class PupilCardModule(val id: Int) {
    LESSON_EVENTS(4),
    OUT_OF_CLASS_EVENTS(5),
    GRADES(6),
    PERIOD_GRADES(7),
    MATRICULATION_GRADES(8),
    EVALUATION_COMPONENTS(9),
    SCHEDULE(10),
    LESSON_HOMEWORK(11),
    HOMEROOM_NOTES(12),
    ADJUSTMENTS(13),
    TRACKING_NOTES(14),
    MATRICULATION_ADJUSTMENTS(32),
    PRIVATE_LESSON_EVENTS(33),
    STUDENT_EVALUATIONS(34),
    GENERAL_DETAILS(38),
    PERSONAL_EDUCATION_PROGRAM(39),
}
