package com.vdovenko.triviaquiz.di

import com.vdovenko.triviaquiz.data.network.api.ApiFactory
import com.vdovenko.triviaquiz.data.network.api.ApiService
import com.vdovenko.triviaquiz.data.repository.RepositoryImpl
import com.vdovenko.triviaquiz.domain.repository.Repository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    singleOf(::RepositoryImpl) { bind<Repository>() }

    single<ApiService> {
        ApiFactory.apiService
    }
}