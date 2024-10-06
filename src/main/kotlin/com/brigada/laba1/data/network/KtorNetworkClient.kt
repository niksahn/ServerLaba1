package com.brigada.laba1.data.network

import com.brigada.laba1.data.entities.Genre
import io.ktor.client.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class KtorNetworkClient(private val client: HttpClient = configureClient()) {
    suspend fun addPrologRecommendationData(
        request: PrologRecommendationData
    ) = client
        .post("http://localhost:8090/add_data") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .status
}

@Serializable
data class PrologRecommendationData(
    val users: List<User>,
    val films: List<Film>
) {
    @Serializable
    data class Film(
        val genre: Genre,
        val movie: String
    )

    @Serializable
    data class User(
        val user: String,
        val movie: String
    )
}

fun configureClient() = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}