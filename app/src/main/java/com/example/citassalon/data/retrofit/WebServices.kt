package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.AppointmentResponse
import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.util.SERVICIOS
import com.example.citassalon.util.STAFFS
import com.example.citassalon.util.SUCURSALES
import okhttp3.Response
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