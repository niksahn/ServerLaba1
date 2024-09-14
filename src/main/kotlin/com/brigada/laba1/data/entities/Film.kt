package com.brigada.laba1.data.entities

data class Film(
    val id: Long,
    val genre: Genre,
    val description: String,
    val name: String,
    val link: String
) {
    enum class Genre { HORROR, DETECTIVE, COMEDY, FANTASY, SI_FI }
}
