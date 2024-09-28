package com.brigada.laba1.data.repository


import com.brigada.laba1.data.entities.FilmMongo
import com.brigada.laba1.data.entities.Genre
import com.brigada.laba1.domain.Film
import com.brigada.laba1.domain.toDomain
import com.brigada.laba1.domain.toMongo
import com.mongodb.client.model.Aggregates.limit
import com.mongodb.client.model.Aggregates.match
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.BsonValue
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

class DataRepositoryMongo(database: MongoDatabase) : DataRepository {

    private val films = database.getCollection<FilmMongo>(collectionName = "films")
    override suspend fun getFilms(): List<Film> = films.find().limit(100).toList().map { it.toDomain() }

    override suspend fun getFilm(id: String): Film? =
        films.find(eq("_id", ObjectId(id))).limit(1).toList().firstOrNull()?.toDomain()

    override suspend fun changeFilm(newFilmData: Film): Boolean {
        val updateParams = Updates.combine(
            Updates.set(FilmMongo::genre.name, newFilmData.genre),
            Updates.set(FilmMongo::description.name, newFilmData.description),
            Updates.set(FilmMongo::name.name, newFilmData.name),
            Updates.set(FilmMongo::link.name, newFilmData.link),
        )
        return try {
            films.updateOne(eq("_id", ObjectId(newFilmData.id)), updateParams).wasAcknowledged()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addFilm(newFilmData: Film) {
        films.insertOne(newFilmData.toMongo())
    }

    override suspend fun addFilm(newFilmData: List<Film>) {
        films.insertMany(newFilmData.map { it.toMongo() })
    }

    override suspend fun deleteFIlm(id: String): Boolean {
        if (films.find(eq("_id", ObjectId(id))).toList().isEmpty()) return false
        return films.deleteOne(eq("_id", ObjectId(id))).wasAcknowledged()
    }

    override suspend fun getRandom(genre: Genre, size: Int): List<Film> {
        return films.aggregate(
            listOf(
                match(eq(FilmMongo::genre.name, genre.toString())),
                BsonDocument("\$sample", BsonDocument("size", BsonInt32(size))),
                limit(500)
            )
        )
            .toList()
            .map { it.toDomain() }
    }

    override suspend fun clear(): Boolean {
        return films.deleteMany(Document()).wasAcknowledged()
    }

    override suspend fun count(): Long = films.countDocuments()
}