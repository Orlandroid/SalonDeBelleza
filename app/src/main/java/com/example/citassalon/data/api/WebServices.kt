package com.example.citassalon.data.api

import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.data.models.remote.migration.SucursalesResponse
import retrofit2.http.GET

interface WebServices {

    @GET("servicios")
    suspend fun getServicios(): List<Servicio>

    @GET("Skeduly/Sucursales.json")
    suspend fun getSucursales(): SucursalesResponse

    @GET("staffs")
    suspend fun getStaff(): List<Staff>

    @GET("appointments")
    suspend fun getAppointMents(): List<AppointmentResponse>

}