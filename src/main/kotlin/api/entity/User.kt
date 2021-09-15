package api.entity

import kotlinx.serialization.Serializable

@Serializable
class User(
    val id: Long,
    val username: String,
    val discriminator: String,
    val avatar: String? = null,
    val defAvatar: String,
    val bio: String? = null,
    val banner: String? = null,
    val social: Social? = null,
    val color: String? = null,
    val supporter: Boolean,
    val certifiedDev: Boolean,
    val mod: Boolean,
    val webMod: Boolean,
    val admin: Boolean
)
