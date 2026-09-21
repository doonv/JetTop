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
