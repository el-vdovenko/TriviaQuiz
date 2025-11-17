package com.vdovenko.triviaquiz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vdovenko.triviaquiz.getApplicationComponent
import com.vdovenko.triviaquiz.presentation.game.GameScreen
import com.vdovenko.triviaquiz.presentation.game.GameViewModel
import com.vdovenko.triviaquiz.ui.theme.TriviaQuizTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val component = getApplicationComponent()
            val viewModel: GameViewModel = viewModel(factory = component.getViewModelFactory())

            TriviaQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//val question = HtmlCompat.fromHtml(questionComp.question, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()