package com.example.domain.state

sealed class ApiResult<T> {
    data class Success<T>(val result: T) : ApiResult<T>()
    data class Error(val error: String) : ApiResult<Any>()
}

fun <T> ApiResult<T>.isSuccess(): Boolean = this is ApiResult.Success
fun <T> ApiResult<T>.isError(): Boolean = this is ApiResult.Error

fun <T> ApiResult<T>.getResult() = (this as ApiResult.Success).result