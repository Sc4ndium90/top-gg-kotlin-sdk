package api.entity

import kotlinx.serialization.Serializable

@Serializable
class Social(
    val youtube: String? = null,
    val reddit: String? = null,
    val twitter: String? = null,
    val instagram: String? = null,
    val github: String? = null
)
