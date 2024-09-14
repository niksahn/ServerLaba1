package com.brigada.laba1.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureHTTP() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
//    install(SwaggerUI) {
//        info {
//            title = "API"
//            version = "latest"
//            description = "Example API for testing and demonstration purposes."
//        }
//        server {
//            url = "http://localhost:8080"
//            description = "Development Server"
//        }
//    }
//    routing {
//        // Create a route for the openapi-spec file.
//        route("api.json") {
//            openApiSpec()
//        }
//        // Create a route for the swagger-ui using the openapi-spec at "/api.json".
//        route("swagger") {
//            swaggerUI("/api.json")
//        }
//    }
}

