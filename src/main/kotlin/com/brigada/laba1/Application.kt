package com.brigada.laba1

import com.brigada.laba1.plugins.configureHTTP
import com.brigada.laba1.plugins.configureKoin
import com.brigada.laba1.plugins.configureSerialization
import com.brigada.laba1.routing.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ) .start(wait = true)}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureRouting()
    configureKoin()
}
