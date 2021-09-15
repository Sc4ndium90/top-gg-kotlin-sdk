package api.entity

import kotlinx.serialization.Serializable

@Serializable
class BotStats(
    var server_count: Int? = null,
    var shards: List<Int>? = null,
    var shard_id: Int? = null,
    var shard_count: Int? = null
)
