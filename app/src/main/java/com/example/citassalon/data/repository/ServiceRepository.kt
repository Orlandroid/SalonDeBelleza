package com.example.citassalon.data.repository

import com.example.citassalon.data.retrofit.WebServices
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val webServices: WebServices) {

    fun getServices() = webServices.getServicios()
}