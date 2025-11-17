package com.vdovenko.triviaquiz.data.mapper

import androidx.core.text.HtmlCompat
import com.vdovenko.triviaquiz.data.network.dto.CategoryDTO
import com.vdovenko.triviaquiz.data.network.dto.QuestionDTO
import com.vdovenko.triviaquiz.domain.entities.Answer
import com.vdovenko.triviaquiz.domain.entities.Category
import com.vdovenko.triviaquiz.domain.entities.Question

fun QuestionDTO.toEntity(): Question {
    val answers = this.incorrectAnswers.map { stringToAnswer(convertText(it)) }.toMutableList()
    answers.add(stringToAnswer(convertText(this.correctAnswer), true))
    answers.shuffle()
    return Question(
        question = question,
        answers = answers
    )
}

fun CategoryDTO.toEntity(): Category = Category(
    id = id,
    name = convertText(name)
)

fun Category.toModel(): CategoryDTO = CategoryDTO(
    id = id,
    name = name
)

fun stringToAnswer(text: String, isCorrect: Boolean = false) = Answer(text, isCorrect)

fun convertText(text: String) =
    HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()