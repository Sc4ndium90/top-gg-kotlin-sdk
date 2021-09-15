package webhook

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import webhook.bot.BotWebhookEvent
import webhook.bot.BotWebhookListener
import webhook.enums.Options
import webhook.enums.VoteType
import webhook.server.ServerWebhookEvent
import webhook.server.ServerWebhookListener
import webhook.utils.RequestManager
import webhook.utils.ResponseManager
import java.util.*
import java.util.function.Consumer


class RequestHandler(private val listenerCollection: ListenerCollection) : HttpHandler {
    override fun handle(httpExchange: HttpExchange) {
        var currOptions: List<Options>? = null
        var validationDisabled = false
        try {
            val reqManager = RequestManager(httpExchange)
            val resManager = ResponseManager(httpExchange)
            val dataString = reqManager.string
            var authorization: String
            try {
                authorization = httpExchange.requestHeaders.getFirst("Authorization")
                if (authorization == null) {
                    authorization = "none"
                }
            } catch (ignored: Exception) {
                authorization = "none"
            }

            // send earlier because everything needed was stored already
            resManager.setResponseCode(200).writeResponse("Received! Thank you :)")
            val listenerObject = listenerCollection.listener
            if (listenerObject is BotWebhookListener) {
                val listener = listenerObject
                val event = BotWebhookEvent(dataString, listener, authorization!!, listenerCollection.authorization)
                val optionsList = listOf(*listenerCollection.options)

                currOptions = optionsList

                if (optionsList.contains(Options.ONLY_LISTEN_FOR_TEST_VOTES)) {
                    if (event.vote.type != VoteType.TEST && event.vote.type != VoteType.INVALID) {
                        return
                    }
                }

                if (event.isAuthorized && event.isValid) {
                    listener.onWebhookRequest(event)
                    return
                }
                if (!event.isAuthorized) {
                    if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                        if (!event.isValid) {
                            if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                                validationDisabled = true
                                listener.onWebhookRequest(event)
                            }
                        } else {
                            listener.onWebhookRequest(event)
                        }
                        return
                    }
                }
                if (!event.isValid) {
                    if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                        validationDisabled = true
                        if (!event.isAuthorized) {
                            if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                                listener.onWebhookRequest(event)
                            }
                        } else {
                            listener.onWebhookRequest(event)
                        }
                    }
                }
            } else if (listenerObject is ServerWebhookListener) {
                val listener: ServerWebhookListener = listenerObject
                val event = ServerWebhookEvent(dataString, listener, authorization, listenerCollection.authorization)
                val optionsList = Arrays.asList(*listenerCollection.options)

                // Exception purposes
                currOptions = optionsList
                if (optionsList.contains(Options.ONLY_LISTEN_FOR_TEST_VOTES)) {
                    if (!event.vote.type.equals(VoteType.TEST) && !event.vote.type
                            .equals(VoteType.INVALID)
                    ) {
                        return
                    }
                }

                //TODO please make this somewhat more efficient
                if (event.isAuthorized && event.isValid) {
                    listener.onWebhookRequest(event)
                    return
                }
                if (!event.isAuthorized) {
                    if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                        if (!event.isValid) {
                            if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                                validationDisabled = true
                                listener.onWebhookRequest(event)
                            }
                        } else {
                            listener.onWebhookRequest(event)
                        }
                        return
                    }
                }
                if (!event.isValid) {
                    if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                        validationDisabled = true
                        if (!event.isAuthorized) {
                            if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                                listener.onWebhookRequest(event)
                            }
                        } else {
                            listener.onWebhookRequest(event)
                        }
                    }
                }
            } else {
                println("Error handling Request")
            }
        } catch (e: Exception) {
            System.err.println("Error while handling request")
            if (validationDisabled) {
                System.err.println("Note: Validation was disabled through the Listener, check first before asking for help!")
                System.err.println("Referring Option: IGNORE_VALIDATION")
            }
            System.err.print("Active Options: ")
            assert(currOptions != null)
            currOptions!!.forEach(Consumer { options: Options ->
                System.err.print(options.name + ", ")
            })
            System.err.println()
            e.printStackTrace()
        }
    }
}