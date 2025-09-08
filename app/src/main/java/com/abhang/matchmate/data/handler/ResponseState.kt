package com.abhang.matchmate.data.handler

sealed class ResponseState<T> {
    data class Success<T>(val data: T, val message: String = "Success") : ResponseState<T>()
    data class Error<T>(val code: Int, val message: String, val data: T? = null) : ResponseState<T>()
    class Loading<T> : ResponseState<T>()
}