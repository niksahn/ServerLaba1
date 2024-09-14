package com.brigada.laba1.data.repository

import com.brigada.laba1.data.entities.Film

class DataRepositoryCollectionImpl : DataRepository {
    private val films = mutableListOf(
        Film(
            0,
            Film.Genre.SI_FI,
            "американский анимационный телесериал, созданный Дэном Хармоном и Джастином Ройландом," +
                    " премьера которого состоялась 2 декабря 2013 года в программном блоке " +
                    "Cartoon Network [adult swim].",
            "Рик и морти",
            "https://rick-i-morty.online/"
        ),
        Film(1, Film.Genre.SI_FI, "фильм Нолана", "Начало", "https://rick-i-morty.online/"),
        Film(2, Film.Genre.DETECTIVE, "детектив", "Шерлок", "https://rick-i-morty.online/"),
    )

    override fun getFilms(): List<Film> = films

    override fun getFilm(id: Long): Film? = films.firstOrNull { it.id == id }

    override fun changeFilm(newFilmData: Film): Boolean {
        if (!films.any { it.id == newFilmData.id }) return false
        films.replaceAll { if (it.id == newFilmData.id) newFilmData else it }
        return true
    }

    override fun addFilm(newFilmData: Film) {
        films.add(newFilmData)
    }

    override fun deleteFIlm(id: Long): Boolean {
        if (!films.any { it.id == id }) return false
        films.removeAll { it.id == id }
        return true
    }
}