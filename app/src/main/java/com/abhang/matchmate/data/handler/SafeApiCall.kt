package com.abhang.matchmate.data.handler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ResponseState<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ResponseState.Success(body)
                } else {
                    ResponseState.Error(-1, "Response body is null", null)
                }
            } else {
                val code = response.code()
                val message = response.message()
                ResponseState.Error(code, "HTTP Error $code: $message", null)
            }
        } catch (e: Throwable) {
            val errorHandler = handleError(e)
            ResponseState.Error(-1,errorHandler.getErrorMessage().toString(), null)
        }
    }
}