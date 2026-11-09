package doonv.jettop.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class PupilCardSettingsRequest(
    @SerialName("id") val studyYear: Int? = null
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
    @Serializable(with = IntBooleanSerializer::class)
    val isCurrent: Boolean,
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

object IntBooleanSerializer : KSerializer<Boolean> {
    override val descriptor =
        PrimitiveSerialDescriptor("IntBoolean", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Boolean =
        decoder.decodeInt() != 0

    override fun serialize(encoder: Encoder, value: Boolean) =
        encoder.encodeInt(if (value) 1 else 0)
}
