package com.vdovenko.triviaquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class QuestionsResponseDTO(
    @SerializedName("response_code") val responseCode: Int,
    @SerializedName("results") val questionsList: List<QuestionDTO>
)
