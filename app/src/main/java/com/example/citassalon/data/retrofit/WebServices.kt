package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.models.Sucursal
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("/servicios")
    suspend fun getServicios(): Call<List<Servicio>>

    @GET("/sucursales")
    suspend fun getSucursales(): Call<List<Sucursal>>


}