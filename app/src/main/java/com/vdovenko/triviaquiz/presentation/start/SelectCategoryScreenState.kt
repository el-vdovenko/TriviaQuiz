package com.vdovenko.triviaquiz.presentation.start

import com.vdovenko.triviaquiz.domain.entities.Category

sealed interface SelectCategoryScreenState {

    data object Initial : SelectCategoryScreenState

    data class CategoriesLoaded(val categories: List<Category>) : SelectCategoryScreenState

    data object Loading : SelectCategoryScreenState

    data class Error(val message: String) : SelectCategoryScreenState
}