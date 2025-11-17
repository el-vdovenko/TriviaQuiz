package com.vdovenko.triviaquiz.domain.entities

data class Question(
    val question: String,
    val answers: List<Answer>,
//    val correctAnswer: String,
//    val incorrectAnswers: List<String>
)
