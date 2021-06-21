package client.entity

import kotlinx.serialization.Serializable

@Serializable
class Bot(
    val defAvatar: String,
    val invite: String? = null,
    val website: String? = null,
    val support: String? = null,
    val github: String? = null,
    val longdesc: String? = null,
    val shortdesc: String,
    val prefix: String,
    @Deprecated("No more information", ReplaceWith("Nothing"), DeprecationLevel.WARNING)
    val lib: String? = null,
    val clientid: Long,
    val avatar: String? = null,
    val id: Long,
    val discriminator: String,
    val username: String,
    val date: String,
    val server_count: Int? = null,
    val shard_count: Int? = null,
    val guilds: List<Long>,
    val shards: List<Long>? = null,
    val monthlyPoints: Int,
    val points: Int,
    val certifiedBot: Boolean,
    val owners: List<Long>,
    val tags: List<String>,
    val bannerUrl: String,
    val donatebotguildid: String?
)
