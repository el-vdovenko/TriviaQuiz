package com.vdovenko.triviaquiz.di.dagger

import com.vdovenko.triviaquiz.data.network.api.ApiFactory
import com.vdovenko.triviaquiz.data.network.api.ApiService
import com.vdovenko.triviaquiz.data.repository.RepositoryImpl
import com.vdovenko.triviaquiz.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService
    }
}