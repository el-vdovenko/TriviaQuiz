package com.vdovenko.triviaquiz.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdovenko.triviaquiz.domain.entities.DataError
import com.vdovenko.triviaquiz.domain.entities.Resource
import com.vdovenko.triviaquiz.domain.usecases.GetCategoriesUseCase
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

    private fun loadCategories() {

        _screenState.value = SelectCategoryScreenState.Loading

        viewModelScope.launch {
            val result = getCategoriesUseCase()

            when(result) {
                is Resource.Success -> {
                    _screenState.value = SelectCategoryScreenState.CategoriesLoaded(result.data)
                }

                is Resource.Error -> {
                    when (result.error) {
                        DataError.Network.NO_INTERNET -> {}
                        DataError.Network.TOO_MANY_REQUESTS, DataError.Api.RATE_LIMIT -> {
                            retryLoad()
                            return@launch
                        }
                        else -> {
                            _screenState.value =
                                SelectCategoryScreenState.Error("${result.error} ${result.message}")
                        }
                    }
                }
            }
        }
    }

    private suspend fun retryLoad() {
        if (_retryCount == 2) {
            _screenState.value = SelectCategoryScreenState.Error("")
            _retryCount = 0
            return
        }
        _retryCount++
        delay(RETRY_DELAY)
        loadCategories()
    }

    companion object {

        private const val RETRY_DELAY = 5000L
    }
}