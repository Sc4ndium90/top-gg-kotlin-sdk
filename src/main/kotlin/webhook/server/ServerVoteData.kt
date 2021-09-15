package webhook.server

import org.json.JSONObject
import webhook.enums.VoteType
import webhook.utils.Query


class ServerVoteData

    (private val data: JSONObject) {
    val userID: Long
        get() = data.getLong("user")

    val guildID: Long
        get() = data.getLong("guild")

    val type: VoteType
        get() {
            val type = data.getString("type")
            return if (type.equals("upvote", ignoreCase = true)) VoteType.UPVOTE else if (type.equals("test",
                    ignoreCase = true)
            ) VoteType.TEST else VoteType.INVALID
        }

    val query: Query
        get() = Query(data.getString("query"))

}