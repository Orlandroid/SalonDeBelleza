package com.example.citassalon.util

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : ApiState<T>(data)
    class Loading<T>(data: T? = null) : ApiState<T>(data)
    class Error<T>(msg: Throwable, data: T? = null) : ApiState<T>(data,msg.toString())

}