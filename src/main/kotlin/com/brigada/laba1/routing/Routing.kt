package com.brigada.laba1.routing

import com.brigada.laba1.domain.DataController
import com.brigada.laba1.routing.models.AddingFIlm
import com.brigada.laba1.routing.models.FilmResponse
import com.brigada.laba1.routing.models.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val controller by inject<DataController>()

    routing {

        get("/films") {
            call.respond(HttpStatusCode.OK, controller.getAllData())
        }

        get("/film/{id}") {
            call.pathParameters["id"]
                ?.let { controller.getById(it) }
                ?.toResponse()
                ?.let { call.respond(HttpStatusCode.OK, it) }
                ?: call.respond(HttpStatusCode.BadRequest)
        }

        post("/film/update") {
            call.receive<FilmResponse>()
                .let { controller.update(it) }
                .let { if (it) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.BadRequest) }
        }

        post("/film/add") {
            try {
                call.receive<AddingFIlm>()
                    .let { controller.addFilm(it) }
                    .let { call.respond(HttpStatusCode.OK) }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        delete("/film/{id}") {
            call.pathParameters["id"]
                ?.let { controller.deleteFilm(it) }
                ?.let { if (it) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.BadRequest) }
        }

        post("/test/{number}") {
            call.pathParameters["number"]
                ?.toIntOrNull()
                ?.let { controller.test(it) }
                ?.let { call.respond(HttpStatusCode.OK) }
                ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}
