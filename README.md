# top-gg-kotlin-sdk
**Kotlin SDK for top.gg API (NON OFFICIAL)**


# Usage
First, we have to build a DiscordBotListAPI object

```kotlin
val api = DiscordBotListAPI.Builder()
  .setToken(yourToken)
  .setBotId(yourBotId)
  .build()
```

**Set web stats**

You have differents way to set stats.

1. Post the server count of the bot
```kotlin
val serverCount: Int = ..
api.setStats(serverCount)
```

2. Post server count for an individual shard
```kotlin
val shardId: Int = ..       // ID of the shard
val shardCount: Int = ..    // Amount of shards
val serverCount: Int = ..   // Amount of server in this shard
api.setStats(shardId, shardCount, serverCount)
```

3. Post the server count of all shards in 
```kotlin
val shards: List<Int> = listOf(..) // List of all the shards' server counts
api.setStats(shards)
```


**Retrieve an user**

```kotlin
val userId: Long = .. // You can also use a String userId
api.getUser(userId)
```


**Retrieve a bot**

```kotlin
val botId: Long = .. // You can also use a String botId
api.getBot(botId)
```

**Check votes**

:warning: It only check if a user has voted at least once on your bot
```kotlin
val userId: Long = .. // You can also use a String userId
if (api.hasVoted(userId)) {
    ..
}
```

**Retrieve weekend multiplier**

```kotlin
api.isWeekend() // Return true or false
```

**Retrieve bot stats**

```kotlin
val botId: Long = .. // You can also use a String botId
api.getStats(botID) 
```

**Retrieve 1000 last voters**

⚠️ If your bot receives more than 1000 votes monthly you cannot use this endpoints and must use wehooks (Hopeful made a library for webhooks [here](https://github.com/Hopeful-Developers/topggwebhooks4j)) and implement your own caching instead.
```kotlin
api.getVotes() 
```
