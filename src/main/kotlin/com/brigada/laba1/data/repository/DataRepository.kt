package com.brigada.laba1.data.repository

import com.brigada.laba1.domain.Film

interface DataRepository {
    suspend fun getFilms(): List<Film>
    suspend fun getFilm(id: String): Film?
    suspend fun changeFilm(newFilmData: Film): Boolean
    suspend fun addFilm(newFilmData: Film)
    suspend fun deleteFIlm(id: String): Boolean
}