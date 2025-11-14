package api.model

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Order(
    val id: Long? = null,
    val petId: Long? = null,
    val quantity: Int? = null,
    val status: String? = null,
    val complete: Boolean? = null,
)
