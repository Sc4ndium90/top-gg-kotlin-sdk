package client

import client.entity.Bot
import client.entity.BotStats
import client.entity.SmallUser
import client.entity.User
import client.impl.DiscordBotListAPIImpl

interface DiscordBotListAPI {

    fun setStats(shardId: Int, shardTotal: Int, serverCount: Int)
    fun setStats(shardServerCount: List<Int>)
    fun setStats(serverCount: Int)

    fun getStats(botId: Long): BotStats?
    fun getStats(botId: String): BotStats?

    fun getUser(userId: Long): User?
    fun getUser(userId: String): User?

    fun getBot(botId: Long): Bot?
    fun getBot(botId: String): Bot?

    fun getVotes(): List<SmallUser>?

    fun hasVoted(userId: Long): Boolean
    fun hasVoted(userId: String): Boolean

    fun isWeekend(): Boolean

    class Builder {
        private var botId: Long? = null
        private var token: String? = null

        fun setToken(token: String): Builder {
            this.token = token
            return this
        }

        fun setBotId(botId: Long): Builder {
            this.botId = botId
            return this
        }

        fun build(): DiscordBotListAPI {
            if (token == null) throw IllegalArgumentException("Token cannot be null !")
            if (botId == null) throw IllegalArgumentException("Bot ID cannot be null !")

            return DiscordBotListAPIImpl(token!!, botId!!)
        }
    }
}
