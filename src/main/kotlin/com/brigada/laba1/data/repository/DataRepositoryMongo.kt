package com.brigada.laba1.data.repository


import com.brigada.laba1.data.entities.Film
import org.bson.Document
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import org.bson.BsonDocument

class DataRepositoryMongo(database: MongoDatabase) : DataRepository {

    private val films = database.getCollection<Film>(collectionName = "films")
    override suspend fun getFilms(): List<Film> = films.find().toList()

    override suspend fun getFilm(id: Long): Film? = films.find(eq("id", id)).limit(1).toList().firstOrNull()

    override suspend fun changeFilm(newFilmData: Film): Boolean {
        val updateParams = Updates.combine(
            Updates.set(Film::genre.name, newFilmData.genre),
            Updates.set(Film::description.name, newFilmData.description),
            Updates.set(Film::name.name, newFilmData.name),
            Updates.set(Film::link.name, newFilmData.link),
        )
        return films.updateOne(eq("id", newFilmData.id), updateParams).wasAcknowledged()
    }

    override suspend fun addFilm(newFilmData: Film) {
        films.insertOne(newFilmData)
    }

    override suspend fun deleteFIlm(id: Long): Boolean = films.deleteOne(eq("id", id)).wasAcknowledged()
}