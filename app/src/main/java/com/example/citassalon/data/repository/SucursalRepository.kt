package com.example.citassalon.data.repository

import com.example.citassalon.data.retrofit.WebServices

class SucursalRepository(private val services: WebServices) {

    suspend fun getSucursales() = services.getSucursales()

    suspend fun cancelService() = services.getServicios().cancel()

}