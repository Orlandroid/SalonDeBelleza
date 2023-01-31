package com.example.citassalon.data.api

import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.data.models.remote.migration.SucursalesResponse
import com.example.citassalon.data.models.remote.ramdomuser.RandomUserResponse
import com.example.citassalon.domain.entities.remote.dummyUsers.DummyUsersResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface WebServices {

    @GET("servicios")
    suspend fun getServicios(): List<Servicio>

    @GET("Skeduly/Sucursales.json")
    suspend fun getSucursales(): SucursalesResponse

    @GET("staffs")
    suspend fun getStaff(): List<Staff>

    @GET
    suspend fun randomUser(
        @Url url: String
    ): RandomUserResponse

    @GET
    suspend fun getStaffUsers(
        @Url url: String
    ): DummyUsersResponse


}