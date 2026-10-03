package doonv.jettop.data

import android.content.Context
import android.text.format.DateUtils
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@PascalCase
data class MessagesInboxRequest(
    // val HasRead = null??
    val labelId: Int,
    val pageId: Int,
    val searchQuery: String
)

@Serializable
data class InboxMessage(
    @SerialName("typeID") val typeId: Int = 0,
    val subject: String? = null,
    val sendingDate: LocalDateTime? = null,
    val userTitle: String? = null,
    @SerialName("student_F_name") val firstName: String? = null,
    @SerialName("student_L_name") val lastName: String? = null,
    val userType: Int = 0,
    val classCode: String? = null,
    val classNumber: Int = 0,
    val hasRead: Int = 0,
    val filesWereAttached: Int = 0,
    val rowNumber: Int = 0,
    val count: Int = 0,
    val fromTitle: String? = null,
    val senderId: String? = null,
    val messageId: String,
    val userImageToken: String? = null
) {
    val senderFullName: String? get() = fullName(firstName, lastName)
    val isRead: Boolean get() = hasRead > 0
    val hasAttachments: Boolean get() = filesWereAttached > 0

    fun dateLabel(context: Context): String = dateLabel(context, sendingDate)

}

@Serializable
@PascalCase
data class MessageDetailsRequest(
    // val HasRead = null??
    val filterId: Int,
    val isInBox: Boolean,
    val messageId: String
)

@Serializable
data class MessageDetails(
    val messageData: MessageData,
    val messagesCount: Int? = null,
    val roles: String? = null,
    // TODO: other stuff
)

@Serializable
data class MessageData(
    val isSystemMessage: Boolean? = null,
    val userImageToken: String? = null,
    val typeId: Int = 0,
    val subject: String? = null,
    val messageContent: String? = null,
    val institutionCode: Int = 0,
    val sendingDate: LocalDateTime? = null,
    val attachedFile: String? = null,
    val attachedFile2: String? = null,
    val attachedFile3: String? = null,
    val attachedFile4: String? = null,
    val attachedFile5: String? = null,
    val isVisible: Boolean = true,
    val signedOn: String? = null,
    val signingClasses: String? = null,
    val filesWereAttached: Int = 0,
    val hiddenRecipients: Int = 0,
    val isMunicipal: Boolean? = null,
    @SerialName("privateName")
    val firstName: String? = null,
    val lastName: String? = null,
    val userType: Int = 0,
    val recipientsName: String? = null,
    val fromTitle: String? = null,
    val totalRecipientsCount: Int = 0,
    val replyDisabled: Boolean? = null,
    val filesList: List<AttachedFile> = emptyList(),
    val parentAllowSign: Boolean? = null,
    val parentAllowSignDate: String? = null,
    val messageAlertId: String? = null,
    val routeData: String? = null,
    val fileName: String? = null,
    val routePage: String? = null,
    val senderId: String? = null,
) {
    val senderFullName: String? get() = fullName(firstName, lastName)

    val hasAttachments: Boolean get() = filesWereAttached > 0

    fun dateLabel(context: Context): String = dateLabel(context, sendingDate)
}

@Serializable
data class AttachedFile(
    val id: Int? = null,
    val fileName: String,
    val fileKey: String? = null,
    val fileUrl: String,
    @SerialName("fileExtantion") // extantion 🥀
    val fileExtension: String? = null,
    val fileSize: FileSize? = null,
)

@Serializable
data class FileSize(
    val size: Double = 0.0,
    // This appears to be some kind of enum internally, but
    // the WebTop client never reads it, so we don't know what it is without
    // probing the server with a ton of different file sizes.
    val sizeType: Int = 0,
    val sizeName: String? = null,
) {
    override fun toString(): String = "$size $sizeName"
}

private fun fullName(firstName: String?, lastName: String?): String? =
    listOfNotNull(firstName, lastName).joinToString(" ").ifEmpty { null }

private val LOCAL = TimeZone.of("Asia/Jerusalem")
private fun dateLabel(context: Context, sendingDate: LocalDateTime?): String {
    return sendingDate
        ?.toInstant(LOCAL)
        ?.toEpochMilliseconds()
        ?.let {
            DateUtils.getRelativeTimeSpanString(
                context, it, false
            ).toString()
        }
        .orEmpty()
}