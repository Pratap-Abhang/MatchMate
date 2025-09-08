package com.abhang.matchmate.data.handler

import retrofit2.HttpException
import java.io.IOException

sealed class ErrorHandler {
    data class NetworkError(val exception: IOException) : ErrorHandler()
    data class ServerError(val code: Int, val message: String) : ErrorHandler()
    data class UnknownError(val throwable: Throwable) : ErrorHandler()
}

fun ErrorHandler.getErrorMessage(): String {
    return when (this) {
        is ErrorHandler.NetworkError -> "Network error occurred: ${exception.message}"
        is ErrorHandler.ServerError -> "Server Error $code: $message"
        is ErrorHandler.UnknownError -> "An unknown error occurred: ${throwable.message}"
    }
}

fun handleError(e: Throwable): ErrorHandler {
    return when (e) {
        is IOException -> ErrorHandler.NetworkError(e)
        is HttpException -> {
            val code = e.code()
            val message = e.message ?: "Server error"
            ErrorHandler.ServerError(code, message)
        }
        else -> ErrorHandler.UnknownError(e)
    }
}