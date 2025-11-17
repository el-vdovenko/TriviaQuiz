package com.vdovenko.triviaquiz.domain.entities

sealed interface Resource<out T, out E: ErrorType> {

    data class Success<out T, out E: ErrorType>(val data: T) : Resource<T, E>
    data class Error<out T, out E: ErrorType>(val error: E, val message: String? = null) : Resource<T, E>
}

