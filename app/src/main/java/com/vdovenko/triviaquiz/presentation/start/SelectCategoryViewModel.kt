package com.vdovenko.triviaquiz.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdovenko.triviaquiz.domain.entities.DataError
import com.vdovenko.triviaquiz.domain.entities.Resource
import com.vdovenko.triviaquiz.domain.usecases.GetCategoriesUseCase
import com.vdovenko.triviaquiz.presentation.additional.asErrorUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectCategoryViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<SelectCategoryScreenState> =
        MutableStateFlow(SelectCategoryScreenState.Initial)
    val screenState: StateFlow<SelectCategoryScreenState> = _screenState

    private var _retryCount = 0

    init {
        loadCategories()
    }

    fun loadCategories() {

        _screenState.value = SelectCategoryScreenState.Loading

        viewModelScope.launch {
            val result = getCategoriesUseCase()

            when(result) {
                is Resource.Success -> {
                    _screenState.value = SelectCategoryScreenState.CategoriesLoaded(result.data)
                }

                is Resource.Error -> {
                    when (result.error) {
                        DataError.Network.TOO_MANY_REQUESTS, DataError.Api.RATE_LIMIT -> {
                            if (_retryCount >= 2) {
                                _screenState.value =
                                    SelectCategoryScreenState.Error(result.asErrorUiText())
                                _retryCount = 0
                            } else {
                                retryLoad()
                                return@launch
                            }
                        }

                        else -> _screenState.value =
                            SelectCategoryScreenState.Error(result.asErrorUiText())
                    }
                }
            }
        }
    }

    private suspend fun retryLoad() {
        _retryCount++
        delay(RETRY_DELAY)
        loadCategories()
    }

    companion object {

        private const val RETRY_DELAY = 5000L
    }
}