package com.brigada.laba1.data.repository

import com.brigada.laba1.data.entities.Film

interface DataRepository {
    suspend fun getFilms(): List<Film>
    suspend fun getFilm(id: Long): Film?
    suspend fun changeFilm(newFilmData: Film): Boolean
    suspend fun addFilm(newFilmData: Film)
    suspend fun deleteFIlm(id: Long): Boolean
}