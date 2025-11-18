package com.vdovenko.triviaquiz.domain.usecases

import com.vdovenko.triviaquiz.domain.repository.Repository

class GetCategoriesUseCase(private val repository: Repository) {


    suspend operator fun invoke() = repository.getCategories()
}