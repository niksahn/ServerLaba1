package com.brigada.laba1.routing.models

import com.brigada.laba1.data.entities.Film
import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    val id: Long,
    val genre: String,
    val description: String,
    val name: String,
    val link: String
)

fun Film.toResponse() = FilmResponse(id, genre.mapToString(), description, name, link)
fun FilmResponse.toData() = Film(id, genres.filterValues { it == genre }.keys.first(), description, name, link)

private fun Film.Genre.mapToString() = genres[this] ?: error("Can't find genre")

internal val genres = mapOf(
    Film.Genre.HORROR to "Хоррор",
    Film.Genre.DETECTIVE to "Детектив",
    Film.Genre.COMEDY to "Комедия",
    Film.Genre.FANTASY to "Фентези",
    Film.Genre.SI_FI to "Научная фантастика"
)
