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

class RequestHandler(private val listenerCollection: ListenerCollection): HttpHandler {

    override fun handle(exchange: HttpExchange) {

        var currOptions: List<Options>? = null
        var validationDisabled = false

        try {

            val reqManager = RequestManager(exchange)
            val resManager = ResponseManager(exchange)
            val dataString = reqManager.string

            var authorization: String
            try {
                authorization = exchange.requestHeaders.getFirst("Authorization")
                if (authorization == null) authorization = "none"
            } catch (ignored: Exception) {
                authorization = "none"
            }

            resManager.setResponseCode(200).writeResponse("Received :)")

            val listenerObject = listenerCollection.listener

            if (listenerObject is BotWebhookListener) {

                val event = BotWebhookEvent(dataString, listenerObject, authorization, listenerCollection.authorization)
                val optionsList = listenerCollection.options.asList()
                currOptions = optionsList

                if (optionsList.contains(Options.ONLY_LISTEN_FOR_TEST_VOTES)) {
                    if (event.vote.type != VoteType.TEST && event.vote.type != VoteType.INVALID) return
                }

                if (event.isAuthorized && event.isValid) {
                    listenerObject.onWebhookRequest(event)
                    return
                }

                if (!event.isAuthorized) {
                    if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                        if (!event.isValid) {
                            if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                                validationDisabled = true
                                listenerObject.onWebhookRequest(event)
                            }
                        } else {
                            listenerObject.onWebhookRequest(event)
                        }
                        return
                    }
                }

                if (!event.isValid) {
                    if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                        validationDisabled = true
                        if (!event.isAuthorized) {
                            if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                                listenerObject.onWebhookRequest(event)
                            }
                        } else {
                            listenerObject.onWebhookRequest(event)
                        }
                    }
                }

            } else if (listenerObject is ServerWebhookListener) {

                val event =
                    ServerWebhookEvent(dataString, listenerObject, authorization, listenerCollection.authorization)
                val optionsList = listenerCollection.options.toList()
                currOptions = optionsList

                if (optionsList.contains(Options.ONLY_LISTEN_FOR_TEST_VOTES)) {
                    if (event.vote.type != VoteType.TEST && event.vote.type != VoteType.INVALID) return
                }

                if (event.isAuthorized && event.isValid) {
                    listenerObject.onWebhookRequest(event)
                    return
                }

                if (!event.isAuthorized) {
                    if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                        if (!event.isValid) {
                            if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                                validationDisabled = true
                                listenerObject.onWebhookRequest(event)
                            }
                        } else {
                            listenerObject.onWebhookRequest(event)
                        }
                    }
                }

                if (!event.isValid) {
                    if (optionsList.contains(Options.IGNORE_VALIDATION)) {
                        validationDisabled = true
                        if (!event.isAuthorized) {
                            if (optionsList.contains(Options.IGNORE_AUTHORIZATION)) {
                                listenerObject.onWebhookRequest(event)
                            }
                        } else {
                            listenerObject.onWebhookRequest(event)
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
            System.err.println("Active Options")

            assert(currOptions != null)
            currOptions!!.forEach { options -> System.err.println(options.name + ", ") }
            System.err.println()
            e.printStackTrace()
        }


    }


}