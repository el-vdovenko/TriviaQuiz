package com.vdovenko.triviaquiz

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vdovenko.triviaquiz.di.dagger.ApplicationComponent
import com.vdovenko.triviaquiz.di.dagger.DaggerApplicationComponent

class TriviaQuizApp: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as TriviaQuizApp).component
}