package com.vdovenko.triviaquiz.presentation.game

import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.presentation.additional.UiText

sealed interface GameScreenState {

    data object Initial : GameScreenState

    data class ShowQuestion(val question: Question) : GameScreenState

    data class ShowAnswer(val question: Question, val answerIndex: Int, val correctIndex: Int) : GameScreenState

    data class Result(val result: Int, val isGood: Boolean) : GameScreenState

    data object Loading : GameScreenState

    data class Error(val error: UiText) : GameScreenState
}