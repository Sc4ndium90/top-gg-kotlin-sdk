package webhook

import webhook.bot.BotWebhookListener
import webhook.enums.Options
import webhook.server.ServerWebhookListener


class WebhookBuilder
/**
 * Constructor
 */
{
    private val listenerStorage = HashMap<String, ListenerCollection>()
    private var port = 6969

    fun setPort(port: Int): WebhookBuilder {
        this.port = port
        return this
    }

    fun addGuildListener(
        webhookPath: String,
        listener: ServerWebhookListener,
        webhookAuth: String,
        vararg options: Options,
    ): WebhookBuilder {
        listenerStorage[webhookPath] = ListenerCollection(listener, webhookAuth, options.toList().toTypedArray())
        return this
    }

    fun addBotListener(
        webhookPath: String,
        listener: BotWebhookListener,
        webhookAuth: String,
        vararg options: Options,
    ): WebhookBuilder {
        listenerStorage[webhookPath] = ListenerCollection(listener, webhookAuth, options.toList().toTypedArray())
        return this
    }

    fun build(): Webhook {
        val packs = ArrayList<PathCollection>()
        listenerStorage.forEach { (s: String, o: ListenerCollection) ->
            packs.add(PathCollection(
                s, o))
        }
        return Webhook(port, packs)
    }
}