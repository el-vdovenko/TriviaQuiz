package com.vdovenko.triviaquiz.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vdovenko.triviaquiz.ui.theme.BlueAccent
import com.vdovenko.triviaquiz.ui.theme.TriviaQuizTypography
import com.vdovenko.triviaquiz.ui.theme.bungeeFont

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onClickStart: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .weight(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 24.dp),
                    style = TriviaQuizTypography.titleMedium,
                    text = "Welcome to"
                )
                Text(
                    style = TriviaQuizTypography.titleLarge,
                    text = "TriviaQuiz"
                )
                Spacer(modifier = Modifier.height(96.dp))
                Button(
                    modifier = Modifier.width(188.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = BlueAccent,
                        contentColor = Color.White
                    ),
                    onClick = { onClickStart() }
                ) {
                    Text(
                        fontFamily = bungeeFont,
                        text = "Start"
                    )
                }
            }
        }
    }
}