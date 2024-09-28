package com.brigada.laba1.data.repository

import com.brigada.laba1.data.entities.Genre
import com.brigada.laba1.domain.Film

interface DataRepository {
    suspend fun getFilms(): List<Film>
    suspend fun getFilm(id: String): Film?
    suspend fun changeFilm(newFilmData: Film): Boolean
    suspend fun addFilm(newFilmData: Film)
    suspend fun addFilm(newFilmData: List<Film>)
    suspend fun deleteFIlm(id: String): Boolean
    suspend fun getRandom(genre: Genre, size: Int): List<Film>
    suspend fun clear(): Boolean
    suspend fun count(): Long
}