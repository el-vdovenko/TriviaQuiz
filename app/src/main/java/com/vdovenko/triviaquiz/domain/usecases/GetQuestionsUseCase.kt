package com.vdovenko.triviaquiz.domain.usecases

import com.vdovenko.triviaquiz.domain.repository.Repository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(
        sessionToken: String? = null,
        categoryId: Int,
        difficulty: String,
        amount: Int
    ) =
        repository.getQuestions(
            sessionToken = sessionToken,
            categoryId = categoryId,
            difficulty = difficulty,
            amount = amount
        )
}