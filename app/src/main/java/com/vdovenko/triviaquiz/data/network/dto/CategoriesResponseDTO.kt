package com.vdovenko.triviaquiz.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoriesResponseDTO(
    @SerializedName("trivia_categories") val categoriesList: List<CategoryDTO>
)