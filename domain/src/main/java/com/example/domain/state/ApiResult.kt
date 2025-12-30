package com.example.domain.state

sealed class ApiResult<T> {
    data class Success<T>(val result: T) : ApiResult<T>()
    data class Error<T>(val error: String? = null) : ApiResult<T>()
}

fun <T> ApiResult<T>.isSuccess(): Boolean = this is ApiResult.Success
fun <T> ApiResult<T>.isError(): Boolean = this is ApiResult.Error

fun <T> ApiResult<T>.getResultOrNull() =
    if (this.isSuccess()) {
        (this as ApiResult.Success).result
    } else {
        null
    }

fun <T> ApiResult<T>.getContent() = (this as ApiResult.Success).result
