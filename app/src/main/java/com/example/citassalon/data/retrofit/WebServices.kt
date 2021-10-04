package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.models.Sucursal
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("servicios/")
    fun getServicios(): Call<List<Servicio>>

    @GET("sucursales/")
    fun getSucursales(): Call<List<Sucursal>>

    @GET("staffs/")
    fun getStaff(): Call<List<Staff>>

}