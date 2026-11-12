package com.example.data.api

import com.example.data.remote.model.BranchesResponseDto
import com.example.data.remote.model.DummyUsersResponseDto
import com.example.data.remote.model.ServiceDto
import com.example.data.remote.model.StaffDto
import retrofit2.http.GET
import retrofit2.http.Url

interface WebServices {
    @GET("servicios")
    suspend fun getServicios(): List<ServiceDto>

    @GET("Skeduly/Sucursales.json")
    suspend fun getSucursales(): BranchesResponseDto

    @GET("staffs")
    suspend fun getStaff(): List<StaffDto>

    @GET
    suspend fun getStaffUsers(
        @Url url: String
    ): DummyUsersResponseDto
}
