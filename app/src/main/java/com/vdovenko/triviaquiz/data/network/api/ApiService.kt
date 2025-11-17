package com.vdovenko.triviaquiz.data.network.api

import com.vdovenko.triviaquiz.data.network.dto.CategoriesResponseDTO
import com.vdovenko.triviaquiz.data.network.dto.QuestionsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("token") sessionToken: String? = null,
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String = "medium",
        @Query("type") type: String = "multiple"
    ): Response<QuestionsResponseDTO>

    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoriesResponseDTO>
}