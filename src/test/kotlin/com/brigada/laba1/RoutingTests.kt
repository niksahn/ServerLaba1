package com.brigada.laba1

import com.brigada.laba1.TestDataModule.testModule
import com.brigada.laba1.data.caching.InMemoryClient
import com.brigada.laba1.data.repository.CachedRepository
import com.brigada.laba1.data.repository.DataRepository
import com.brigada.laba1.data.repository.DataRepositoryMongo
import com.brigada.laba1.data.utils.configurateClient
import com.brigada.laba1.data.utils.configureMongoDB
import com.brigada.laba1.domain.DataController
import com.brigada.laba1.plugins.configureHTTP
import com.brigada.laba1.plugins.configureSerialization
import com.brigada.laba1.routing.configureRouting
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutingTest {
    @Test
    fun testGetAllFilms() = testApplication {
        application { testModule() }
        client.get("/films").apply { assertEquals(HttpStatusCode.OK, status) }
    }

    @Test
    fun add10_000() = testApplication {
        application { testModule() }
        val start = client.get("/films/count").body<Int>()
        client.post("/test/10000").apply { assertEquals(HttpStatusCode.OK, status) }
        val end = client.get("/films/count").body<Int>()
        assertEquals(start + 10_000, end)
    }

    @Test
    fun add100_000() = testApplication {
        application { testModule() }
        val start = client.get("/films/count").body<Int>()
        client.post("/test/100000").apply { assertEquals(HttpStatusCode.OK, status) }
        val end = client.get("/films/count").body<Int>()
        assertEquals(start + 100_000, end)
    }

    @Test
    fun delete() = testApplication {
        application { testModule() }
        val start = client.get("/films/count").body<Int>()
        assert(start > 0)
        client.delete("/films").apply { assertEquals(HttpStatusCode.OK, status) }
        val end = client.get("/films/count").body<Int>()
        assertEquals(end, 0)
    }
}

object TestDataModule {
    private val data = module {
        single<DataRepository>(createdAtStart = true) {
            CachedRepository(
                repository = DataRepositoryMongo(configureMongoDB(configurateClient(), "Test")),
                redisClient = InMemoryClient()
            )
        }
        single<DataController>(createdAtStart = true) { DataController(get()) }
    }

    fun Application.testModule() {
        configureSerialization()
        configureHTTP()
        configureRouting()
        configureTestKoin()
    }

    private fun Application.configureTestKoin() {
        install(Koin) {
            slf4jLogger()
            modules(data)
        }
    }
}
