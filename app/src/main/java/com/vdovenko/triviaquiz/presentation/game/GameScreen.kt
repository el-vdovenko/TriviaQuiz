package com.vdovenko.triviaquiz.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vdovenko.triviaquiz.domain.entities.Answer
import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.getApplicationComponent
import com.vdovenko.triviaquiz.ui.theme.AccentBlue
import com.vdovenko.triviaquiz.ui.theme.AccentGreen
import com.vdovenko.triviaquiz.ui.theme.AnswerButton
import com.vdovenko.triviaquiz.ui.theme.ExtraDarkBlue
import com.vdovenko.triviaquiz.ui.theme.LightBlue
import com.vdovenko.triviaquiz.ui.theme.TriviaQuizTheme
import com.vdovenko.triviaquiz.ui.theme.bungeeFont

@Composable
fun GameScreen(
    modifier: Modifier = Modifier
) {

    val component = getApplicationComponent()
    val viewModel: GameViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState by viewModel.screenState.collectAsState()
    val totalQuestions by viewModel.totalQuestions.collectAsState()
    val correctAnswers by viewModel.correctAnswer.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        GameHeader(
            correctAnswer = correctAnswers,
            totalQuestions = totalQuestions
        )

        when (val currentState = screenState) {
            GameScreenState.Initial -> {
                Text("Initial")
            }

            GameScreenState.Loading -> {
                Text("Loading")
            }

            is GameScreenState.ShowQuestion -> {
                ShowQuestion(
                    modifier = modifier,
                    question = currentState.question,
                    onAnswerClick = { viewModel.countAnswer(it) }
                )
            }

            is GameScreenState.ErrorScreen -> {
                Text(currentState.error)
            }
        }
    }
}

@Composable
private fun ShowQuestion(
    modifier: Modifier = Modifier,
    question: Question,
    onAnswerClick: (Answer) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        //Question
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = question.question,
                textAlign = TextAlign.Center
            )
        }

        //Answers
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            question.answers.forEach { answer ->
                AnswerButton(
                    text = answer.text,
                    onClick = { onAnswerClick(answer) })
            }
        }
    }
}

@Composable
private fun GameHeader(
    correctAnswer: Int,
    totalQuestions: Int
) {
    Row(
        modifier = Modifier
            .padding(8.dp, 32.dp, 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                contentDescription = null,
                tint = AccentBlue
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = LightBlue,
            fontFamily = bungeeFont,
            text = "Question $totalQuestions"
        )
        Text(
            fontFamily = bungeeFont,
            color = LightBlue,
            fontSize = 16.sp,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = AccentGreen)) {
                    append(correctAnswer.toString())
                }
                append("/$totalQuestions")
            }
        )
    }
}

@Preview
@Composable
private fun PreviewShowQuestion() {
    TriviaQuizTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ExtraDarkBlue)
        ) {
            ShowQuestion(
                question = Question(
                    question = "//Question",
                    answers = listOf(
                        Answer("Answer 1", false),
                        Answer("Answer 2", false),
                        Answer("Answer 3", true),
                        Answer("Answer 4", false)
                    )
                ),
                    onAnswerClick = { }
                )
        }
    }
}

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp)
//                .height(204.dp),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f),
//                horizontalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                AnswerButton(
//                    modifier = Modifier.weight(1f),
//                    text = "Answer 1"
//                )
//                AnswerButton(
//                    modifier = Modifier.weight(1f),
//                    text = "Answer 2"
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f),
//                horizontalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                AnswerButton(
//                    modifier = Modifier.weight(1f),
//                    text = "Answer 3"
//                )
//                AnswerButton(
//                    modifier = Modifier.weight(1f),
//                    text = "Answer 4"
//                )
//            }
//        }