package com.brigada.laba1.data.entities

import org.bson.codecs.pojo.annotations.BsonId

data class Film(
    @BsonId
    val id: Long,
    val genre: Genre,
    val description: String,
    val name: String,
    val link: String
) {
    enum class Genre { HORROR, DETECTIVE, COMEDY, FANTASY, SI_FI }
}
