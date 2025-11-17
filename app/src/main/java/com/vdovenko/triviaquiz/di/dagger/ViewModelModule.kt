package com.vdovenko.triviaquiz.di.dagger

import androidx.lifecycle.ViewModel
import com.vdovenko.triviaquiz.presentation.game.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(GameViewModel::class)
    @Binds
    fun bindGameViewModel(viewModelModule: GameViewModel): ViewModel
}