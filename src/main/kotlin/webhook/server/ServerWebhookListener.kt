package webhook.server

interface ServerWebhookListener {
    fun onWebhookRequest(event: ServerWebhookEvent?)
}