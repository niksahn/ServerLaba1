package com.brigada.laba1.data.repository

import com.brigada.laba1.data.caching.CacheClient
import com.brigada.laba1.data.caching.RedisClient
import com.brigada.laba1.data.entities.Genre
import com.brigada.laba1.domain.Film
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable

class CachedRepository(
    private val repository: DataRepository,
    private val redisClient: CacheClient
) : DataRepository {
    override suspend fun getFilms(): List<Film> =
        redisClient.get(ALL, ListSerializer(Film.serializer()))
            ?: repository.getFilms()
                .also { redisClient.set(ALL, it, ListSerializer(Film.serializer()), EXPIRY_IN_SECONDS) }

    override suspend fun getFilm(id: String): Film? =
        redisClient.get(getFilmName(id), Film.serializer())
            ?: repository.getFilm(id)
                .also { redisClient.set(getFilmName(id), it, Film.serializer().nullable, EXPIRY_IN_SECONDS) }

    override suspend fun changeFilm(newFilmData: Film): Boolean =
        repository.changeFilm(newFilmData).also { redisClient.delete(getFilmName(newFilmData.id)) }

    override suspend fun addFilm(newFilmData: Film) = repository.addFilm(newFilmData)
    override suspend fun addFilm(newFilmData: List<Film>) {
        repository.addFilm(newFilmData)
    }

    override suspend fun deleteFIlm(id: String): Boolean =
        repository.deleteFIlm(id).also { redisClient.delete(getFilmName(id)) }

    override suspend fun getRandom(genre: Genre, size: Int): List<Film> = repository.getRandom(genre, size)

    override suspend fun clear(): Boolean =
        repository.clear().also { redisClient.clear() }

    override suspend fun count(): Long = repository.count()

    companion object {
        private const val ALL = "all"
        private const val FILM_ = "film"
        private const val EXPIRY_IN_SECONDS = 60L
        private fun getFilmName(id: String) = "$FILM_$id"
    }
}