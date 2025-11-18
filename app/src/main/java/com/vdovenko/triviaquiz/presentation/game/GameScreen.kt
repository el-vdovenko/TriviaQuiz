package com.vdovenko.triviaquiz.presentation.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vdovenko.triviaquiz.domain.entities.Answer
import com.vdovenko.triviaquiz.domain.entities.Category
import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.ui.theme.AnswerButton
import com.vdovenko.triviaquiz.ui.theme.BlueAccent
import com.vdovenko.triviaquiz.ui.theme.BlueCard
import com.vdovenko.triviaquiz.ui.theme.BlueLight
import com.vdovenko.triviaquiz.ui.theme.GreenAccent
import com.vdovenko.triviaquiz.ui.theme.GreenCard
import com.vdovenko.triviaquiz.ui.theme.GreenCardBorder
import com.vdovenko.triviaquiz.ui.theme.GreenLight
import com.vdovenko.triviaquiz.ui.theme.RedCard
import com.vdovenko.triviaquiz.ui.theme.RedCardBorder
import com.vdovenko.triviaquiz.ui.theme.RedLight
import com.vdovenko.triviaquiz.ui.theme.bungeeFont
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    selectCategoryId: Int
) {

    val viewModel: GameViewModel = koinViewModel { parametersOf(selectCategoryId) }

    val screenState by viewModel.screenState.collectAsState()
    val totalQuestions by viewModel.totalQuestions.collectAsState()
    val correctAnswers by viewModel.correctAnswer.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
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
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = BlueCard,
                        trackColor = BlueAccent
                    )
                }

            }

            is GameScreenState.ShowQuestion -> {
                ShowQuestion(
                    question = currentState.question,
                    onAnswerClick = { answer, index -> viewModel.checkAnswer(answer, index) }
                )
            }

            is GameScreenState.ShowAnswer -> {
                ShowAnswer(
                    question = currentState.question,
                    answerIndex = currentState.answerIndex,
                    correctIndex = currentState.correctIndex
                )
            }

            is GameScreenState.Error -> {
                Text(currentState.error)
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
                tint = BlueAccent
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = BlueLight,
            fontFamily = bungeeFont,
            text = "Question $totalQuestions"
        )
        Text(
            fontFamily = bungeeFont,
            color = BlueLight,
            fontSize = 16.sp,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = GreenAccent)) {
                    append(correctAnswer.toString())
                }
                append("/$totalQuestions")
            }
        )
    }
}

@Composable
private fun ShowQuestion(
    modifier: Modifier = Modifier,
    question: Question,
    onAnswerClick: (Answer, Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        //Question
        Box(
            modifier = Modifier
                .padding(16.dp)
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
            question.answers.forEachIndexed { index, answer ->
                AnswerButton(
                    text = answer.text,
                    onClick = { onAnswerClick(answer, index) })
            }
        }
    }
}

@Composable
private fun ShowAnswer(
    modifier: Modifier = Modifier,
    question: Question,
    answerIndex: Int,
    correctIndex: Int
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        //Question
        Box(
            modifier = Modifier
                .padding(16.dp)
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
            question.answers.forEachIndexed { index, answer ->

                if (index == correctIndex) {
                    AnswerButton(
                        text = answer.text,
                        containerColor = GreenCard,
                        borderColor = GreenCardBorder,
                        textColor = GreenLight,
                        onClick = { },
                        enabled = false)
                } else if (correctIndex != answerIndex && index == answerIndex) {
                    AnswerButton(
                        text = answer.text,
                        containerColor = RedCard,
                        borderColor = RedCardBorder,
                        textColor = RedLight,
                        onClick = { },
                        enabled = false)
                } else {
                    AnswerButton(
                        text = answer.text,
                        onClick = { },
                        enabled = false)
                }
            }
        }
    }
}