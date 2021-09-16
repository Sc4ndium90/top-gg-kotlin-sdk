[![](https://jitpack.io/v/Sc4ndium90/top-gg-kotlin-sdk.svg)](https://jitpack.io/#Sc4ndium90/top-gg-kotlin-sdk)

# Top.gg Kotlin SDK
**Kotlin SDK for top.gg API (NOT OFFICIAL)**

This library is based on [topggwebhook4j](https://github.com/Hopeful-Developers/topggwebhooks4j) by Hopeful and [java-jdk](https://github.com/top-gg/java-sdk)

## Summary
1. [Download](#download)
2. [Top.gg API](#topgg-api-usage)
3. [Webhook](#webhook)

## Download
**Maven**
```xml
<repositories>
	<repository>
		 <id>jitpack.io</id>
		 <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```xml
<dependency>
	<groupId>com.github.Sc4ndium90</groupId>
	<artifactId>top-gg-kotlin-sdk</artifactId>
	<version>VERSION</version>
</dependency>
```

**Gradle**
```gradle
repositories {
    ..
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Sc4ndium90:top-gg-kotlin-sdk:VERSION'
}
```

**Gradle KTS**
```gradle
repositories {
    ..
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.Sc4ndium90:top-gg-kotlin-sdk:VERSION")
}
```


# TopGG API Usage
First, we have to build a DiscordBotListAPI object

```kotlin
val api = DiscordBotListAPI.Builder()
  .setToken(yourToken)
  .setBotId(yourBotId)
  .build()
```

## **Set web stats**

There are so many ways of posting bot stats.

1. Post the bot's server count
```kotlin
val serverCount: Int = ..
api.setStats(serverCount)
```

2. Post server count for a single shard
```kotlin
val shardId: Int = ..       // ID of the shard
val shardCount: Int = ..    // Amount of shards
val serverCount: Int = ..   // Amount of servers in this shard
api.setStats(shardId, shardCount, serverCount)
```

3. Post server count for several shards
```kotlin
val shards: List<Int> = listOf(..) // List of all the shards' server count
api.setStats(shards)
```


## **Retrieve a user**

```kotlin
val userId: Long = .. // You can also use a String
api.getUser(userId)
```


## **Retrieve a bot**

```kotlin
val botId: Long = .. // You can also use a String
api.getBot(botId)
```

## **Check votes**

:warning: It only checks if a user has voted at least once on your bot
```kotlin
val userId: Long = .. // You can also use a String
if (api.hasVoted(userId)) {
    ..
}
```

## **Retrieve weekend multiplier**

```kotlin
api.isWeekend() // Returns true or false
```

## **Retrieve bot stats**

```kotlin
val botId: Long = .. // You can also use a String
api.getStats(botID) 
```

## **Retrieve 1000 last voters**

⚠️ If your bot receives more than 1000 monthly votes, you cannot use this endpoint and must use webhooks and implement your own caching instead.
```kotlin
api.getVotes() 
```

# Webhook
First, we have to build a Webhook object

⚠️ To use webhooks you need to open the wanted port to receive votes ! (Top.GG IP : `159.203.105.187`)

```kotlin
val webhook = WebhookBuilder()
  .setPort(4242)
  .addBotListener(context, listener, auth, options)
  .addGuildListener(context, listener, auth, options)
  .build()
webhook.start()
```

## Options for #addBotListener and #addGuildListener
Parameter | Type | Example | Purpose
--------- | ---- | ------- | -------
context | String | "BotWebhook" | This is the path of the webhook. The link on top.gg would be like `http://XXXXXX:4242/BotWebhook`
listener | BotWebhookListener or ServerWebhookListener | `BotVoteListener()` | This is the class you would use to give rewards to users or to send a small notification
auth | String | "MyCoolWebhookKey" | This is what authorizes your webhook. This is used to identify the requests coming to your webhook.
options | Options.. | `Options.IGNORE_AUTHORIZATION` | These are optional and are useful for people who want to check if the webhook works.

## Create the listener
To create a vote listener, just create a class and add `BotWehbookListener` or `ServerWebhookListener` to it!

```kotlin
class VoteEvent : BotWebhookListener {
   override fun onWebhookRequest(event: BotWebhookEvent) {
      // Do anything you want with the vote event!
   }
}
```
