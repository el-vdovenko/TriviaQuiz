package com.vdovenko.triviaquiz.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdovenko.triviaquiz.domain.entities.Answer
import com.vdovenko.triviaquiz.domain.entities.DataError
import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.domain.entities.Resource
import com.vdovenko.triviaquiz.domain.usecases.GetQuestionsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<GameScreenState> =
        MutableStateFlow(GameScreenState.Initial)
    val screenState: StateFlow<GameScreenState> = _screenState

    private val _correctAnswers = MutableStateFlow(0)
    val correctAnswer: StateFlow<Int> = _correctAnswers

    private val _totalQuestions = MutableStateFlow(0)
    val totalQuestions: MutableStateFlow<Int> = _totalQuestions


    private val _questionsStorage = MutableStateFlow<List<Question>>(emptyList())

    private val _currentIndex = MutableStateFlow(0)

    private var _isLoadingQuestions: Boolean = false

    private var _retryCount = 0


    init {
        _screenState.value = GameScreenState.Loading
        loadQuestions()
    }

    private fun loadQuestions() {

        if (_isLoadingQuestions) {
            return
        }

        _isLoadingQuestions = true

        viewModelScope.launch {

            val result = getQuestionsUseCase(
                categoryId = 11,
                difficulty = "medium",
                amount = QUESTIONS_LOAD_AMOUNT
            )
            _isLoadingQuestions = false

            when (result) {
                is Resource.Success -> {
                    _questionsStorage.value += result.data
                    _retryCount = 0

                    if (_screenState.value == GameScreenState.Loading) {
                        nextQuestion()
                    }
                }

                is Resource.Error -> {
                    when (result.error) {
                        DataError.Network.NO_INTERNET -> {}
                        DataError.Network.TOO_MANY_REQUESTS, DataError.Api.RATE_LIMIT -> {
                            retryLoad()
                            return@launch
                        }
                        else -> {
                            _screenState.value =
                                GameScreenState.ErrorScreen("${result.error} ${result.message}")
                        }
                    }
                }
            }
        }
    }

    private fun nextQuestion() {

        if (_currentIndex.value >= _questionsStorage.value.size - 1) {
            _screenState.value = GameScreenState.Loading
            if (!_isLoadingQuestions) {
                loadQuestions()
            }
            return
        }

        _totalQuestions.value++
        _screenState.value =
            GameScreenState.ShowQuestion(_questionsStorage.value[_currentIndex.value])
        _currentIndex.value++

        if (_currentIndex.value == _questionsStorage.value.size - 2) {
            loadQuestions()
        }
    }

    fun checkAnswer(answer: Answer, answerIndex: Int) {

        val question = (_screenState.value as GameScreenState.ShowQuestion).question
        val correctIndex = question.answers.indexOfFirst { it.isCorrect }
        _screenState.value = GameScreenState.ShowAnswer(
            question = question,
            answerIndex = answerIndex,
            correctIndex = correctIndex
        )

        if (answer.isCorrect) {
            _correctAnswers.value++
        }
        viewModelScope.launch {
            delay(DELAY_FOR_NEXT_QUESTION)
            nextQuestion()
        }
    }

    private suspend fun retryLoad() {
        if (_retryCount == 2) {
            _screenState.value = GameScreenState.ErrorScreen("")
            _retryCount = 0
            return
        }
        _retryCount++
        delay(RETRY_DELAY)
        loadQuestions()
    }

    companion object {

        private const val QUESTIONS_LOAD_AMOUNT = 20
        private const val RETRY_DELAY = 5000L
        private const val DELAY_FOR_NEXT_QUESTION = 1000L
    }
}
