package com.vdovenko.triviaquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
