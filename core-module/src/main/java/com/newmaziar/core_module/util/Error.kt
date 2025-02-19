package com.newmaziar.core_module.util

import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getString
import com.newmaziar.core_module.R
import com.newmaziar.core_module.runtime.ApplicationContext


interface Error

sealed class ErrorResult : Error {
    data object Reset : ErrorResult()
    data class GenericError(
        val message: String = getString(
            ApplicationContext.appContext,
            R.string.an_unknown_network_error_occurred
        )
    ) : ErrorResult()
    data class NetworkError(val type: NetworkErrorType) : ErrorResult()

}


/**
 * Enum class representing different types of network errors.
 */
enum class NetworkErrorType(@StringRes val message: Int) {

    NoInternet(R.string.no_internet_connection),

    Timeout(R.string.request_timed_out),

    NotFound(R.string.resource_not_found),

    Unauthorized(R.string.unauthorized_access),

    InternalServerError(R.string.internal_server_error),

    BadRequest(R.string.bad_request),

    Unknown(R.string.an_unknown_network_error_occurred),

    ParsingError(R.string.error_parsing_data),
}