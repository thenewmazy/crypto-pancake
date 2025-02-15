package com.newmaziar.cryptopancake.core.util

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Fail(val reason: Error) : ResultWrapper<Nothing>()
}

inline fun <T, R> ResultWrapper<T>.mapResponse(convert: (T) -> R): ResultWrapper<R> {
    return when (this) {
        is ResultWrapper.Fail -> this
        is ResultWrapper.Success -> ResultWrapper.Success(convert(value))
    }
}