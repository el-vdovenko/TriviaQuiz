package com.vdovenko.triviaquiz.di

import com.vdovenko.triviaquiz.domain.usecases.GetCategoriesUseCase
import com.vdovenko.triviaquiz.domain.usecases.GetQuestionsUseCase
import com.vdovenko.triviaquiz.domain.usecases.GetTokenUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetCategoriesUseCase(repository = get()) }

    factory { GetQuestionsUseCase(repository = get()) }

    factory { GetTokenUseCase(repository = get()) }
}