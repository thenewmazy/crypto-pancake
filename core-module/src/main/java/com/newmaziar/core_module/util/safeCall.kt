package com.newmaziar.core_module.util

import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ResultWrapper.Success(body)
            } else {
                ResultWrapper.Fail(ErrorResult.GenericError("${response.code()}: ${response.message()}"))
            }
        } else {
            ResultWrapper.Fail(ErrorResult.GenericError("${response.code()}: ${response.message()}"))
        }
    } catch (e: Exception) {
        when (e) {
            is UnknownHostException -> ErrorResult.NetworkError(type = NetworkErrorType.NoInternet)
            is SocketTimeoutException -> ErrorResult.NetworkError(type = NetworkErrorType.Timeout)
            is IOException -> ErrorResult.NetworkError(type = NetworkErrorType.NoInternet)
            else -> ErrorResult.NetworkError(type = NetworkErrorType.Unknown)
        }.let {
            ResultWrapper.Fail(it)
        }
    }
}

