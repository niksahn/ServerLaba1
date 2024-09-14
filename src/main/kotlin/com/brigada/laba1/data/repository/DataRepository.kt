package com.brigada.laba1.data.repository

import com.brigada.laba1.data.entities.Film

interface DataRepository {
    fun getFilms(): List<Film>
    fun getFilm(id: Long): Film?
    fun changeFilm(newFilmData: Film): Boolean
    fun addFilm(newFilmData: Film)
    fun deleteFIlm(id: Long): Boolean
}