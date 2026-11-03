package doonv.jettop.data

import kotlinx.serialization.Serializable

@Serializable
data class Cached<T>(
    val data: T,
    val updatedAt: Long,
)
