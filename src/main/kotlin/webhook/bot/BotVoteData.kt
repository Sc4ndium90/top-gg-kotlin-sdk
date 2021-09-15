package webhook.bot

import org.json.JSONObject
import webhook.enums.VoteType
import webhook.utils.Query


class BotVoteData(data: JSONObject) {
    private val data: JSONObject

    val userID: Long
        get() = data.getLong("user")

    val botID: Long
        get() = data.getLong("bot")

    val type: VoteType
        get() {
            val type: String = data.getString("type")
            return if (type.equals("upvote", ignoreCase = true)) VoteType.UPVOTE else if (type.equals("test",
                    ignoreCase = true)
            ) VoteType.TEST else VoteType.INVALID
        }

    val isWeekend: Boolean
        get() = data.getBoolean("isWeekend")

    val query: Query
        get() = Query(data.getString("query"))

    init {
        this.data = data
    }
}