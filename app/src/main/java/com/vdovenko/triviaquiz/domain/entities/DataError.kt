package com.vdovenko.triviaquiz.domain.entities

sealed interface DataError : ErrorType {

    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        EMPTY_BODY,
        UNKNOWN
    }

    enum class Api: DataError {
        NO_RESULTS,
        INVALID_PARAMETER,
        TOKEN_NOT_FOUND,
        TOKEN_EMPTY,
        RATE_LIMIT,
        UNKNOWN_CODE
    }
}