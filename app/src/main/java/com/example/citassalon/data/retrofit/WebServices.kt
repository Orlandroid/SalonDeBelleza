package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.util.SERVICIOS
import com.example.citassalon.util.STAFFS
import com.example.citassalon.util.SUCURSALES
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET(SERVICIOS)
    fun getServicios(): Call<List<Servicio>>

    @GET(SUCURSALES)
    fun getSucursales(): Call<List<Sucursal>>

    @GET(STAFFS)
    fun getStaff(): Call<List<Staff>>

}