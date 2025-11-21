package com.vdovenko.triviaquiz.presentation.start

import com.vdovenko.triviaquiz.domain.entities.Category
import com.vdovenko.triviaquiz.presentation.additional.UiText

sealed interface SelectCategoryScreenState {

    data object Initial : SelectCategoryScreenState

    data class CategoriesLoaded(val categories: List<Category>) : SelectCategoryScreenState

    data object Loading : SelectCategoryScreenState

    data class Error(val error: UiText) : SelectCategoryScreenState
}