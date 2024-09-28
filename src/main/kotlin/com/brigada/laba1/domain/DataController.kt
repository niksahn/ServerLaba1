package com.brigada.laba1.domain

import com.brigada.laba1.data.repository.DataRepository
import com.brigada.laba1.routing.models.*

class DataController(
    private val dataRepository: DataRepository
) {
    suspend fun getAllData() = dataRepository.getFilms().map { it.toResponse() }
    suspend fun addFilm(film: AddingFIlm) = dataRepository.addFilm(film.toData())
    suspend fun deleteFilm(id: String): Boolean = dataRepository.deleteFIlm(id)
    suspend fun getById(id: String) = dataRepository.getFilm(id)?.toResponse()
    suspend fun update(film: FilmResponse): Boolean = dataRepository.changeFilm(film.toData())
    fun genereRandom(number: Int): List<Film> = List(number) { Film.random() }
    suspend fun addRange(films: List<Film>) = dataRepository.addFilm(films)
    suspend fun test(number: Int) = addRange(genereRandom(number))
    suspend fun getRandom(genre: String, size: Int) =
        genre.toGenre()?.let { dataRepository.getRandom(it, size) }?.map { it.toResponse() }

    suspend fun deleteAll() = dataRepository.clear()
    suspend fun count() = dataRepository.count()
}
