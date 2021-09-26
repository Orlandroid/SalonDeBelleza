package com.example.citassalon.util

import com.example.citassalon.data.models.Sucursal

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:List<Sucursal>) : ApiState()
}