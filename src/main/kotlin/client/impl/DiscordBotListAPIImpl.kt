package client.impl

import client.DiscordBotListAPI
import client.entity.*
import client.io.ResponseTransformer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL


class DiscordBotListAPIImpl(val token: String, val botId: Long) : DiscordBotListAPI {

    override fun setStats(shardId: Int, shardTotal: Int, serverCount: Int) {
        post("https://top.gg/api/bots/$botId/stats", Json.encodeToString(BotStats(serverCount, null, shardId, shardTotal)))
    }

    override fun setStats(shardServerCount: List<Int>) {
        post("https://top.gg/api/bots/$botId/stats", Json.encodeToString(BotStats(null, shardServerCount)))
    }

    override fun setStats(serverCount: Int) {
        post("https://top.gg/api/bots/$botId/stats", Json.encodeToString(BotStats(serverCount)))
    }

    override fun getStats(botId: Long): BotStats? {
        return getStats(botId.toString())
    }

    override fun getStats(botId: String): BotStats? {
        return get("https://top.gg/api/bots/$botId/stats")
    }

    override fun getUser(userId: Long): User? {
        return getUser(userId.toString())
    }

    override fun getUser(userId: String): User? {
        return get("https://top.gg/api/users/$userId")
    }

    override fun getBot(botId: Long): Bot? {
        return getBot(botId.toString())
    }

    override fun getBot(botId: String): Bot? {
        return get("https://top.gg/api/bots/$botId")
    }

    override fun getVotes(botId: Long): List<SmallUser>? {
        return getVotes(botId.toString())
    }

    override fun getVotes(botId: String): List<SmallUser>? {
        return get("https://top.gg/api/bots/$botId/votes")
    }

    override fun hasVoted(userId: Long): Boolean {
        return hasVoted(userId.toString())
    }

    override fun hasVoted(userId: String): Boolean {
        return get<Check>("https://top.gg/api/bots/$botId/check?userId=$userId")!!.voted == 1
    }

    override fun isWeekend(): Boolean {
        return get<Weekend>("https://top.gg/api/weekend")!!.is_weekend
    }

    private inline fun <reified T> get(url: String): T? {
        val con = URL(url).openConnection() as HttpURLConnection
        con.requestMethod = "GET"

        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Authorization", token)
        con.connectTimeout = 5000
        con.readTimeout = 5000

        return ResponseTransformer().transform(con.inputStream)
    }

    private fun post(url: String, params: String) {
        val con = URL(url).openConnection() as HttpURLConnection
        con.requestMethod = "POST"

        println(params)

        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Accept", "application/json")
        con.setRequestProperty("Authorization", token)
        con.connectTimeout = 5000
        con.doOutput = true

        con.outputStream.use { os ->
            val input: ByteArray = params.toByteArray()
            os.write(input, 0, input.size)
        }
    }

}