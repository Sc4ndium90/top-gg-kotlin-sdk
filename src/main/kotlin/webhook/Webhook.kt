package webhook

import com.sun.net.httpserver.HttpServer
import webhook.bot.BotWebhookListener
import webhook.server.ServerWebhookListener
import java.io.IOException
import java.net.InetSocketAddress
import java.util.function.Consumer


class Webhook(
    val port: Int,
    private val pathCollections: ArrayList<PathCollection>,
) {

    private lateinit var server: HttpServer

    @Throws(IOException::class)
    fun start(): Webhook {
        server = HttpServer.create(InetSocketAddress(port), 0)
        pathCollections.forEach { pathCollection: PathCollection ->
            val pack = pathCollection.listenerCollection
            server.createContext(pathCollection.context, RequestHandler(pack))
        }
        println("Webhook started under " + server.address.hostString + " on port " + server.address.port + " under the following context | listeners | authorization")
        pathCollections.forEach(Consumer { pathCollection: PathCollection ->
            val listenerCollection = pathCollection.listenerCollection
            when (listenerCollection.listener) {
                is ServerWebhookListener -> println("> " + pathCollection.context + " - (GUILD) " + listenerCollection.listener.javaClass.simpleName + " - " + listenerCollection.authorization)
                is BotWebhookListener -> println("> " + pathCollection.context + " - (BOT) " + listenerCollection.listener.javaClass.simpleName + " - " + listenerCollection.authorization)
                else -> println("severe warning, listener from " + listenerCollection.listener.javaClass.simpleName + " is not type of GuildWebhookListener/BotWebhookListener!")

            }
        })
        server.start()
        return this
    }

}