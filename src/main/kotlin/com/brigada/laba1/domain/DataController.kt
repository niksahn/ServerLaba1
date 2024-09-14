package com.brigada.laba1.domain

import com.brigada.laba1.data.repository.DataRepository
import com.brigada.laba1.routing.models.AddingFIlm
import com.brigada.laba1.routing.models.FilmResponse
import com.brigada.laba1.routing.models.toData
import com.brigada.laba1.routing.models.toResponse

class DataController(
    private val dataRepository: DataRepository
) {
    fun getAllData() = dataRepository.getFilms().map { it.toResponse() }
    fun addFilm(film: AddingFIlm) = dataRepository.addFilm(film.toData(getAllData().last().id + 1))
    fun deleteFilm(id: Long): Boolean = dataRepository.deleteFIlm(id)
    fun getById(id: Long) = dataRepository.getFilm(id)
    fun update(film: FilmResponse):Boolean = dataRepository.changeFilm(film.toData())
}
