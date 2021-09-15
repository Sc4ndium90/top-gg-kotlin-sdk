package api.entity

import kotlinx.serialization.Serializable

@Serializable
class Check(
    val voted: Int? = null
)
