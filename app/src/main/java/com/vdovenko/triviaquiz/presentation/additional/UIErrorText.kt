package com.vdovenko.triviaquiz.presentation.additional

import com.vdovenko.triviaquiz.R
import com.vdovenko.triviaquiz.domain.entities.DataError
import com.vdovenko.triviaquiz.domain.entities.Resource

fun DataError.asUiText(message: String? = null): UiText {

    return when (this) {
        DataError.Api.NO_RESULTS -> UiText.StringResource(
            R.string.error_no_results
        )

        DataError.Api.RATE_LIMIT, DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_too_many_requests
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )

        else -> UiText.StringResource(
            R.string.error_general,
            arrayOf("$this ${message ?: ""}")
        )
    }
}

fun Resource.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText(this.message)
}