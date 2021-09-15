package webhook.bot

import org.json.JSONObject

class BotWebhookEvent(
    val requestString: String,
    listener: BotWebhookListener, requestAuthorization: String,
    var listenerAuthorization: String,
) {

    val requestAuthorization: String
    val listener: BotWebhookListener
    var isValid = false

    val vote: BotVoteData
        get() = BotVoteData(JSONObject(requestString))

    val isAuthorized: Boolean
        get() = requestAuthorization == listenerAuthorization

    init {
        this.listener = listener
        this.requestAuthorization = requestAuthorization
        isValid = try {
            val `object` = JSONObject(requestString)
            `object`["bot"]
            `object`["user"]
            `object`["type"]
            `object`["isWeekend"]
            `object`["query"]
            true
        } catch (e: Exception) {
            false
        }
    }
}