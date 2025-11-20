package com.vdovenko.triviaquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class TokenResponseDTO(
    @SerializedName("response_code") val responseCode: Int,
    @SerializedName("response_message") val responseMessage: String,
    @SerializedName("token") val token: String
    )
