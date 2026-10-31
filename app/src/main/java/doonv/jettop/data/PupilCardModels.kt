package doonv.jettop.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PupilCardSettingsRequest(
    @SerialName("id") val studyYear: Int
)

@Serializable
data class PupilCardSettings(
    val modulesList: List<PupilCardModuleSettings>,
    val divisionsList: List<PupilCardDivision>,
    val children: List<PupilCardChild>,
    val isParent: Boolean,
    val numBackYearsAllowed: Int,
    val currentStudyYear: Int,
)

@Serializable
data class PupilCardModuleSettings(
    val id: Int,
    val title: String,
    val universalTitle: String,
    val isLinked: Int,
//    val classes: JsonElement? = null,
)

@Serializable
data class PupilCardDivision(
    @SerialName("division_id") val divisionId: Int,
    @SerialName("division_name") val divisionName: String,
    @SerialName("year_division") val yearDivision: Int,
    @SerialName("from_date") val fromDate: LocalDateTime,
    @SerialName("to_date") val toDate: LocalDateTime,
    val isPublic: Boolean,
    val isCurrent: Int,
    val viewOrder: Int,
)

@Serializable
data class PupilCardChild(
    val id: String,
    val firstName: String,
    val lastName: String,
    val classCode: Int,
    val classNum: Int,
    val cellphone: String? = null,
)
