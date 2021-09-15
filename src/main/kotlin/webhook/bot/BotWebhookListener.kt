package webhook.bot

interface BotWebhookListener {
    fun onWebhookRequest(event: BotWebhookEvent?)
}