package com.brigada.laba1.data.messaging

import com.brigada.laba1.data.entities.Recommendation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

class RedisMessageClient : Messaging {
    private val jedis: Jedis = Jedis("redis", 6379)

    override fun subscribe(channel: String): Flow<Messaging.Message> {
        val subscriber = RedisSubscriber()
        jedis.subscribe(subscriber, channel)
        return subscriber.flow
    }
}

class PrologMessaging(
    private val client: Messaging
) {
    suspend fun getLastMessage() =
        client.subscribe("recommendations")
            .first()
            .message
            .let { Json.decodeFromString(ListSerializer(Recommendation.serializer()), it) }
}

interface Messaging {
    fun subscribe(channel: String): Flow<Message>
    data class Message(
        val channel: String,
        val message: String
    )
}

class RedisSubscriber : JedisPubSub() {
    private val _flow = MutableSharedFlow<Messaging.Message>()
    val flow = _flow.asSharedFlow()
    override fun onMessage(channel: String, message: String) {
        _flow.tryEmit(Messaging.Message(channel, message))
    }
}
