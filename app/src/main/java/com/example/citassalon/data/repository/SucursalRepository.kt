package com.example.citassalon.data.repository

import com.example.citassalon.data.retrofit.WebServices
import javax.inject.Inject

class SucursalRepository @Inject constructor(private val services: WebServices) {

    suspend fun getSucursales() = services.getSucursales()

}