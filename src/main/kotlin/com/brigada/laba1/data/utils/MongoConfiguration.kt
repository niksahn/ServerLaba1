package com.brigada.laba1.data.utils

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.runBlocking
import org.bson.Document

fun configurateClient(): MongoClient {
    val connectionString = System.getenv("MONGO_URL") ?: "mongodb://localhost:27017"

    val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString))
        .build()

    return MongoClient.create(mongoClientSettings)
}

fun configureMongoDB(client: MongoClient): MongoDatabase {
    client.use { mongoClient ->
        val database = mongoClient.getDatabase("admin")
        runBlocking {
          println( database.runCommand(Document("ping", 1)))
        }
        return database
    }
}