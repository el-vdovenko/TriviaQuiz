package com.vdovenko.triviaquiz.presentation.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vdovenko.triviaquiz.getApplicationComponent

@Composable
fun GameScreen(
    modifier: Modifier = Modifier
) {

    val component = getApplicationComponent()
    val viewModel: GameViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState by viewModel.screenState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when(val currentState = screenState) {
            GameScreenState.Initial -> {
                Text("Initial")
            }

            GameScreenState.Loading -> {
                Text("Loading")
            }

            is GameScreenState.ShowQuestion -> {
                val question = currentState.question
                Text(question.question)
                question.answers.forEach {
                    Text(it.text)
                }
                Button(
                    onClick = {
                        viewModel.nextQuestion()
                    }
                ) {
                    Text("Next")
                }
            }

            is GameScreenState.ErrorScreen -> {
                Text(currentState.error)
            }
        }
    }
}