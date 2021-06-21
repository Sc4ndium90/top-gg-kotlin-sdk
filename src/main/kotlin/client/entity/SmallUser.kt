package client.entity

import kotlinx.serialization.Serializable

@Serializable
class SmallUser(
    val username: String,
    val id: Long,
    var avatar: String
)