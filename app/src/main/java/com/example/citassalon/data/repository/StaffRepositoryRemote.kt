package com.example.citassalon.data.repository

import com.example.citassalon.data.retrofit.WebServices
import javax.inject.Inject

class StaffRepositoryRemote @Inject constructor(private val webServices: WebServices) {

    fun getStaffs() = webServices.getStaff()
}