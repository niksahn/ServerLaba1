package com.brigada.laba1.di

import com.brigada.laba1.data.repository.DataRepository
import com.brigada.laba1.data.repository.DataRepositoryCollectionImpl
import com.brigada.laba1.domain.DataController
import org.koin.dsl.module

object DataModule {
    val data = module {
        single<DataRepository> { DataRepositoryCollectionImpl() }
        single<DataController> { DataController(get()) }
    }
}