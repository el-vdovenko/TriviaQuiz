package com.vdovenko.triviaquiz.domain.repository

import com.vdovenko.triviaquiz.domain.entities.Category
import com.vdovenko.triviaquiz.domain.entities.DataError
import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.domain.entities.Resource

interface Repository {

    suspend fun getCategories(): Resource<List<Category>, DataError>

    suspend fun getQuestions(
        sessionToken: String?,
        categoryId: Int?,
        difficulty: String,
        amount: Int
    ): Resource<List<Question>, DataError>

    suspend fun getToken(): Resource<String, DataError>
}