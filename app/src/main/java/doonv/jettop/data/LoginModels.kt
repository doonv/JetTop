package doonv.jettop.data

import android.util.Base64
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Reverse engineered from WebTop's `RsaService` class in `main.7294e58efaf6cff7.js`
 */
fun encryptDataField(username: String, counter: Int): String {
    val key = SecretKeySpec(
        "01234567890000000150778345678901".toByteArray(Charsets.UTF_8),
        "AES"
    )
    val iv = IvParameterSpec(
        "6543210987654321".toByteArray(Charsets.UTF_8)
    )
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key, iv)
    val json = Json.encodeToString("$username$counter")
    return Base64.encodeToString(
        cipher.doFinal(json.toByteArray(Charsets.UTF_8)), Base64.NO_WRAP
    )
}

@Serializable
data class LoginRequest(
    @SerialName("userName")
    val username: String,
    val password: String,
    val data: String,
    val captcha: String = "", // seems like it can just be "" and still give you a token
    val rememberMe: Boolean = false,
    val biometricLogin: String = "",
    val uniqueId: String = "",
    val deviceDataJson: String = ""
) {
    companion object {
        fun forLogin(
            username: String,
            password: String,
            counter: Int = 0,
            captcha: String = "",
            rememberMe: Boolean = false,
            biometricLogin: String = "",
            uniqueId: String = "",
            deviceDataJson: String = ""
        ) = LoginRequest(
            username = username,
            password = password,
            data = encryptDataField(username, counter),
            captcha = captcha,
            rememberMe = rememberMe,
            biometricLogin = biometricLogin,
            uniqueId = uniqueId,
            deviceDataJson = deviceDataJson
        )
    }
}

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
    val institutionCode: Int,
    val isReseted: Boolean = false,
    val lastResetDate: String? = null,
    val lastPasswordChangeDate: String? = null,
    val lastLoginDate: String? = null,
    val classCode: String, // grade number?
    val classNumber: Int? = null,
    val isSchoolyAdministrator: Boolean = false,
    val token: String,
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
        encoder.encodeBoolean(
            when (value) {
                Gender.MALE -> false
                Gender.FEMALE -> true
            }
        )
}