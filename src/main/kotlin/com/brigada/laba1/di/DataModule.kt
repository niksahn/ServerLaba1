package com.brigada.laba1.di

import com.brigada.laba1.data.caching.RedisClient
import com.brigada.laba1.data.repository.CachedRepository
import com.brigada.laba1.data.repository.DataRepository
import com.brigada.laba1.data.repository.DataRepositoryCollectionImpl
import com.brigada.laba1.data.repository.DataRepositoryMongo
import com.brigada.laba1.data.utils.configurateClient
import com.brigada.laba1.data.utils.configureMongoDB
import com.brigada.laba1.domain.DataController
import org.koin.dsl.module

object DataModule {
    val data = module {
        single<DataRepository>(createdAtStart = true) {
            CachedRepository(
                repository = DataRepositoryMongo(configureMongoDB(configurateClient())),
                redisClient = RedisClient()
            )
        }
        single<DataController>(createdAtStart = true) { DataController(get()) }
    }
}