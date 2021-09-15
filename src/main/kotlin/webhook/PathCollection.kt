package webhook

import webhook.bot.BotWebhookListener
import webhook.enums.ListenerType
import webhook.server.ServerWebhookListener

class PathCollection(val context: String, val listenerCollection: ListenerCollection) {
    val listenerType: ListenerType
        get() = when (listenerCollection.listener) {
            is BotWebhookListener -> ListenerType.BOT
            is ServerWebhookListener -> ListenerType.GUILD
            else -> ListenerType.UNKNOWN
        }
}