package com.vdovenko.triviaquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class QuestionDTO(
//    @SerializedName("type") val type: String = "",
//    @SerializedName("difficulty") val difficulty: String = "",
//    @SerializedName("category") val category: Int,
    @SerializedName("question") val question: String = "",
    @SerializedName("correct_answer") val correctAnswer: String = "",
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String> = listOf()
)