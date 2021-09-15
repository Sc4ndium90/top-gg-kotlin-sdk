package webhook.server

import org.json.JSONObject


class ServerWebhookEvent(
    val requestString: String,
    listener: ServerWebhookListener, requestAuthorization: String,
    var listenerAuthorization: String,
) {

    val requestAuthorization: String

    private val listener: ServerWebhookListener

    var isValid = false

    val vote: ServerVoteData
        get() = ServerVoteData(JSONObject(requestString))

    fun getListener(): ServerWebhookListener {
        return listener
    }

    val isAuthorized: Boolean
        get() = requestAuthorization == listenerAuthorization

    init {
        this.listener = listener
        this.requestAuthorization = requestAuthorization
        isValid = try {
            val `object` = JSONObject(requestString)
            `object`.getLong("guild")
            `object`.getLong("user")
            `object`.getString("type")
            `object`.getString("query")
            true
        } catch (e: Exception) {
            false
        }
    }
}