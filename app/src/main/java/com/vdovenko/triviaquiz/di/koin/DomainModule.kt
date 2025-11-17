package com.vdovenko.triviaquiz.di.koin

import com.vdovenko.triviaquiz.domain.usecases.GetCategoriesUseCase
import com.vdovenko.triviaquiz.domain.usecases.GetQuestionsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetCategoriesUseCase(repository = get()) }

    factory { GetQuestionsUseCase(repository = get()) }
}