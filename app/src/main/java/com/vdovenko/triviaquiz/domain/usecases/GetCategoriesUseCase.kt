package com.vdovenko.triviaquiz.domain.usecases

import com.vdovenko.triviaquiz.domain.repository.Repository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: Repository
) {


    suspend operator fun invoke() = repository.getCategories()
}