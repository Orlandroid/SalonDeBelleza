package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.models.Sucursal
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("/servicios")
    fun getServicios(): Call<Servicio>

    @GET("/sucursales")
    fun getSucursales(): Call<Sucursal>


}