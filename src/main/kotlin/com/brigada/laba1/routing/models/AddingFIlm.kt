package com.brigada.laba1.routing.models

import com.brigada.laba1.data.entities.Film
import kotlinx.serialization.Serializable

@Serializable
data class AddingFIlm(
    val genre: String,
    val description: String,
    val name: String,
    val link: String
)

fun AddingFIlm.toData(id: Long) = Film(id, genres.filterValues { it == genre }.keys.first(), description, name, link)
