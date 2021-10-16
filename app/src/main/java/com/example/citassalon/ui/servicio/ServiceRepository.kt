package com.example.citassalon.ui.servicio

import com.example.citassalon.data.retrofit.WebServices
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val webServices: WebServices) {

    suspend fun getServices() = webServices.getServicios()

}