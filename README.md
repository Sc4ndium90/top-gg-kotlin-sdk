# top-gg-kotlin-sdk
**Kotlin SDK for top.gg API (NOT OFFICIAL)**

#getBots will be added soon

# Usage
First, we have to build a DiscordBotListAPI object

```kotlin
val api = DiscordBotListAPI.Builder()
  .setToken(yourToken)
  .setBotId(yourBotId)
  .build()
```

**Set web stats**

There are so many different ways of posting bot stats.

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


**Retrieve a user**

```kotlin
val userId: Long = .. // You can also use a String
api.getUser(userId)
```


**Retrieve a bot**

```kotlin
val botId: Long = .. // You can also use a String
api.getBot(botId)
```

**Check votes**

:warning: It only checks if a user has voted at least once on your bot
```kotlin
val userId: Long = .. // You can also use a String
if (api.hasVoted(userId)) {
    ..
}
```

**Retrieve weekend multiplier**

```kotlin
api.isWeekend() // Returns true or false
```

**Retrieve bot stats**

```kotlin
val botId: Long = .. // You can also use a String
api.getStats(botID) 
```

**Retrieve 1000 last voters**

⚠️ If your bot receives more than 1000 monthly votes, you cannot use this endpoint and must use webhooks (Hopeful made a library for webhooks [here](https://github.com/Hopeful-Developers/topggwebhooks4j)) and implement your own caching instead.
```kotlin
api.getVotes() 
```
