package com.vdovenko.triviaquiz.presentation.game

import com.vdovenko.triviaquiz.domain.entities.Question

sealed class GameScreenState {

    data object Initial : GameScreenState()

    data class ShowQuestion(val question: Question) : GameScreenState()

    data class ShowAnswer(val question: Question, val answerIndex: Int, val correctIndex: Int) : GameScreenState()

    data object Loading : GameScreenState()

    data class ErrorScreen(val error: String) : GameScreenState()
}