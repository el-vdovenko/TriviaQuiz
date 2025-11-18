package com.vdovenko.triviaquiz

import android.app.Application
import com.vdovenko.triviaquiz.di.applicationModule
import com.vdovenko.triviaquiz.di.dataModule
import com.vdovenko.triviaquiz.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TriviaQuizApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TriviaQuizApp)
            modules(listOf(applicationModule, dataModule, domainModule))
        }
    }
}