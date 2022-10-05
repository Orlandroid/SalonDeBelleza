package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.data.models.remote.Sucursal
import retrofit2.http.GET

interface WebServices {

    @GET("servicios")
    suspend fun getServicios(): List<Servicio>

    @GET("sucursales")
    suspend fun getSucursales(): List<Sucursal>

    @GET("staffs")
    suspend fun getStaff(): List<Staff>

    @GET("appointments")
    suspend fun getAppointMents(): List<AppointmentResponse>

}