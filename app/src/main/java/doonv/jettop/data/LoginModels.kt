package doonv.jettop.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class LoginRequest(
    val userName: String,
    val password: String,
    val data: String = "",
    val captcha: String = "", // seems like it can just be "" and still give you a token
    val rememberMe: Boolean = false,
    val biometricLogin: String = "",
    val uniqueId: String = "",
    val deviceDataJson: String = ""
)

@Serializable
data class LoginResponse(
    val status: Boolean = false,
    val data: LoginData? = null,
    val message: String? = null,
    val errorId: String? = null,
    val errorDescription: String? = null,
    val errorHTML: String? = null
)

@Serializable
data class LoginData(
    val studentId: Long? = null,
    val id: String? = null,
    val userId: String? = null,
    val schoolName: String? = null,
    val isSuperAdmin: Boolean = false,
    val isAllowDataImport: Boolean = false,
    val mustChangePassword: Boolean = false,
    val userType: Int = 0,
    val userImageToken: String? = null,
    val fullLog: Boolean = false,
    val firstName: String? = null,
    val lastName: String? = null,
    val isTeacher: Int = 0,
    val isWebTopUser: Boolean = false,
    val schoolId: Int? = null,
    val studentEmail: String? = null,
    val studentGender: Gender = Gender.MALE,
    val firstInstitutionCode: Int? = null,
    val institutionCode: Int? = null,
    val isReseted: Boolean = false,
    val lastResetDate: String? = null,
    val lastPasswordChangeDate: String? = null,
    val lastLoginDate: String? = null,
    val classCode: String? = null, // grade number?
    val classNumber: Int? = null,
    val isSchoolyAdministrator: Boolean = false,
    val token: String? = null,
    val cellphone: String? = null,
    val browserLanguage: String? = null,
    val initialUserType: Int = 0,
    val fullName: String? = null,
    val isOrt: Boolean = false
)

@Serializable
data class DeviceData(
    val isMobile: Boolean = false,
    val isTablet: Boolean = false,
    val isDesktop: Boolean = true,
    val getDeviceType: String = "Desktop",
    val os: String = "Linux",
    val osVersion: String = "unknown",
    val browser: String = "Firefox",
    val browserVersion: String = "155.0",
    val browserMajorVersion: Int = 155,
    val screen_resolution: String = "1920 x 1080",
    val cookies: Boolean = true,
    val userAgent: String = "Mozilla/5.0 (X11; Linux x86_64; rv:155.0) Gecko/20100101 Firefox/155.0"
)

@Serializable(GenderSerializer::class)
enum class Gender {
    MALE, FEMALE
}

object GenderSerializer : KSerializer<Gender> {
    override val descriptor = PrimitiveSerialDescriptor("Gender", PrimitiveKind.BOOLEAN)

    override fun deserialize(decoder: Decoder) =
        when (decoder.decodeBoolean()) {
            false -> Gender.MALE
            true -> Gender.FEMALE
        }

    override fun serialize(encoder: Encoder, value: Gender) =
        encoder.encodeBoolean(when (value) {
            Gender.MALE -> false
            Gender.FEMALE -> true
        })
}