package com.vdovenko.triviaquiz.di.dagger

import com.vdovenko.triviaquiz.ViewModelFactory
import com.vdovenko.triviaquiz.presentation.MainActivity
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory
}