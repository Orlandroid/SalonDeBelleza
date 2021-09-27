package com.example.citassalon.util

import com.example.citassalon.data.models.Sucursal

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : ApiState<T>()
    class Loading<T>(data: T? = null) : ApiState<T>()
    class Error<T>(msg: Throwable, data: T? = null) : ApiState<T>()

}