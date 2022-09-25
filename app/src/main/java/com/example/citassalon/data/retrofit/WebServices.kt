package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.data.models.remote.Sucursal
import com.example.citassalon.util.SERVICIOS
import com.example.citassalon.util.STAFFS
import com.example.citassalon.util.SUCURSALES
import retrofit2.http.GET

interface WebServices {

    @GET(SERVICIOS)
    suspend fun getServicios(): List<Servicio>

    @GET(SUCURSALES)
    suspend fun getSucursales(): List<Sucursal>

    @GET(STAFFS)
    suspend fun getStaff(): List<Staff>

    @GET("appointments")
    suspend fun getAppointMents(): List<AppointmentResponse>



}