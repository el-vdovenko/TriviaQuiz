package com.vdovenko.triviaquiz.presentation.game

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vdovenko.triviaquiz.R
import com.vdovenko.triviaquiz.domain.entities.Answer
import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.presentation.ui.theme.AnswerButton
import com.vdovenko.triviaquiz.presentation.ui.theme.BlueAccent
import com.vdovenko.triviaquiz.presentation.ui.theme.BlueCard
import com.vdovenko.triviaquiz.presentation.ui.theme.BlueCardBorder
import com.vdovenko.triviaquiz.presentation.ui.theme.BlueLight
import com.vdovenko.triviaquiz.presentation.ui.theme.GreenAccent
import com.vdovenko.triviaquiz.presentation.ui.theme.GreenCard
import com.vdovenko.triviaquiz.presentation.ui.theme.GreenCardBorder
import com.vdovenko.triviaquiz.presentation.ui.theme.GreenLight
import com.vdovenko.triviaquiz.presentation.ui.theme.RedAccent
import com.vdovenko.triviaquiz.presentation.ui.theme.RedCard
import com.vdovenko.triviaquiz.presentation.ui.theme.RedCardBorder
import com.vdovenko.triviaquiz.presentation.ui.theme.RedLight
import com.vdovenko.triviaquiz.presentation.ui.theme.bungeeFont
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    selectCategoryId: Int?,
    onTryAgainClick: () -> Unit,
    onBackClick: () -> Unit
) {

    val viewModel: GameViewModel = koinViewModel { parametersOf(selectCategoryId) }

    val screenState by viewModel.screenState.collectAsState()
    val totalQuestions by viewModel.totalQuestions.collectAsState()
    val correctAnswers by viewModel.correctAnswers.collectAsState()
    val isGameActive by viewModel.isGameActive.collectAsState()

    val alphaHeader = if (isGameActive) 1f else 0f

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        GameHeader(
            correctAnswers = correctAnswers,
            totalQuestions = totalQuestions,
            onEndClick = { viewModel.endGame() },
            alpha = alphaHeader
        )

        when (val currentState = screenState) {
            GameScreenState.Initial -> {

            }

            GameScreenState.Loading -> {
                Box(
                    modifier = Modifier
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

            is GameScreenState.Result -> {
                Result(
                    result = currentState.result,
                    totalQuestions = totalQuestions,
                    correctAnswers = correctAnswers,
                    isGood = currentState.isGood,
                    onTryAgainClick = onTryAgainClick
                )
            }

            is GameScreenState.Error -> {
                ErrorState(
                    modifier = Modifier.weight(1f),
                    isGameActive = isGameActive,
                    error = currentState.error.asString(),
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
private fun GameHeader(
    correctAnswers: Int,
    totalQuestions: Int,
    onEndClick: () -> Unit,
    alpha: Float
) {
    Row(
        modifier = Modifier
            .padding(20.dp, 32.dp, 8.dp)
            .alpha(alpha)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontFamily = bungeeFont,
            color = BlueLight,
            fontSize = 16.sp,
            text = buildAnnotatedString {
                append(stringResource(R.string.header_result))
                append(" ")
                withStyle(style = SpanStyle(color = GreenAccent)) {
                    append(correctAnswers.toString())
                }
                append("/$totalQuestions")
            }
        )
        TextButton(
            onClick = { onEndClick() },
            colors = ButtonDefaults.textButtonColors().copy(
                contentColor = RedAccent
            )
        ) {
            Text(
                fontFamily = bungeeFont,
                text = stringResource(R.string.button_end_game)
            )
        }
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
        Question(
            modifier = Modifier.weight(1f),
            text = question.question
        )

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

    val correctColor = remember { Animatable(BlueCardBorder) }
    val incorrectColor = remember { Animatable(BlueCardBorder) }
    LaunchedEffect(Unit) {
        correctColor.animateTo(
            targetValue = GreenCardBorder,
            animationSpec = infiniteRepeatable(
                animation = tween(200),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    LaunchedEffect(Unit) {
        incorrectColor.animateTo(
            targetValue = RedCardBorder,
            animationSpec = tween(200)
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        //Question
        Question(
            modifier = Modifier.weight(1f),
            text = question.question
        )

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
                        borderColor = correctColor.value,
                        textColor = GreenLight,
                        onClick = { },
                        enabled = false
                    )
                } else if (correctIndex != answerIndex && index == answerIndex) {
                    AnswerButton(
                        text = answer.text,
                        containerColor = RedCard,
                        borderColor = incorrectColor.value,
                        textColor = RedLight,
                        onClick = { },
                        enabled = false
                    )
                } else {
                    AnswerButton(
                        text = answer.text,
                        onClick = { },
                        enabled = false
                    )
                }
            }
        }
    }
}

@Composable
private fun Result(
    modifier: Modifier = Modifier,
    result: Int,
    totalQuestions: Int,
    correctAnswers: Int,
    isGood: Boolean,
    onTryAgainClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val resultColor = if (isGood) GreenAccent else RedAccent
        Text(
            fontFamily = bungeeFont,
            fontSize = 22.sp,
            color = Color.White,
            text = stringResource(R.string.result_title)
        )
        Text(
            fontFamily = bungeeFont,
            fontSize = 22.sp,
            color = resultColor,
            text = "$result%"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .padding(start = 32.dp, end = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = BlueLight,
                text = stringResource(R.string.label_total_questions)
            )
            Text(
                fontFamily = bungeeFont,
                color = BlueLight,
                text = "$totalQuestions"
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 32.dp, end = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = BlueLight,
                text = stringResource(R.string.label_correct_answers)
            )
            Text(
                fontFamily = bungeeFont,
                color = BlueLight,
                text = "$correctAnswers"
            )
        }
        Spacer(modifier = Modifier.height(96.dp))
        Button(
            modifier = Modifier.width(188.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = BlueAccent,
                contentColor = Color.White
            ),
            onClick = { onTryAgainClick() }
        ) {
            Text(
                fontFamily = bungeeFont,
                text = stringResource(R.string.button_play_again)
            )
        }
    }
}

@Composable
private fun ErrorState(
    modifier: Modifier = Modifier,
    isGameActive: Boolean,
    error: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(
                start = 32.dp,
                end = 32.dp,
                bottom = 24.dp,
                top = 0.dp
            ),
            color = BlueLight,
            textAlign = TextAlign.Center,
            text = "Oops! $error"
        )
        if (!isGameActive) {
            TextButton(
                onClick = { onBackClick() }
            ) {
                Text(
                    color = BlueAccent,
                    text = stringResource(R.string.button_back)
                )
            }
        }
    }
}


// Inners Components
@Composable
private fun Question(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = BlueLight
        )
    }
}