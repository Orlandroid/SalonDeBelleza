package com.example.data.api


import com.example.domain.entities.remote.Servicio
import com.example.domain.entities.remote.dummyUsers.DummyUsersResponse
import com.example.domain.entities.remote.Staff
import com.example.domain.entities.remote.migration.SucursalesResponse
import com.example.domain.perfil.RandomUserResponse
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