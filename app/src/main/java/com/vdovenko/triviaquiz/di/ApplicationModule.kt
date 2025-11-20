package com.vdovenko.triviaquiz.di

import com.vdovenko.triviaquiz.presentation.game.GameViewModel
import com.vdovenko.triviaquiz.presentation.start.SelectCategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val applicationModule = module {

    viewModelOf(::SelectCategoryViewModel)

    viewModel { parameters -> GameViewModel(get(), get(), selectedCategoryId = parameters.getOrNull()) }
}