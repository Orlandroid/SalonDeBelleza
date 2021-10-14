package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.util.SERVICIOS
import com.example.citassalon.util.STAFFS
import com.example.citassalon.util.SUCURSALES
import retrofit2.Response
import retrofit2.http.GET

interface WebServices {

    @GET(SERVICIOS)
    suspend fun getServicios(): Response<List<Servicio>>

    @GET(SUCURSALES)
    suspend fun getSucursales(): Response<List<Sucursal>>

    @GET(STAFFS)
    suspend fun getStaff(): Response<List<Staff>>

}