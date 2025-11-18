package com.vdovenko.triviaquiz.di

import com.vdovenko.triviaquiz.presentation.game.GameViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val applicationModule = module {

    viewModelOf(::GameViewModel)
}