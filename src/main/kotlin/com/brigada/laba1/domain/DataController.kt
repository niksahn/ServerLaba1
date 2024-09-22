package com.brigada.laba1.domain

import com.brigada.laba1.data.repository.DataRepository
import com.brigada.laba1.routing.models.AddingFIlm
import com.brigada.laba1.routing.models.FilmResponse
import com.brigada.laba1.routing.models.toData
import com.brigada.laba1.routing.models.toResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataController(
    private val dataRepository: DataRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    suspend fun getAllData() = dataRepository.getFilms().map { it.toResponse() }
    suspend fun addFilm(film: AddingFIlm) = dataRepository.addFilm(film.toData())
    suspend fun deleteFilm(id: String): Boolean = dataRepository.deleteFIlm(id)
    suspend fun getById(id: String) = dataRepository.getFilm(id)
    suspend fun update(film: FilmResponse): Boolean = dataRepository.changeFilm(film.toData())

    suspend fun test(number: Int) {
       // scope.launch {
            for (i in 0 until number) {
                dataRepository.addFilm(Film.random())
            }
      //  }
    }
}
