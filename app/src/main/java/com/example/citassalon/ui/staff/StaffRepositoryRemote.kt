package com.example.citassalon.ui.staff

import com.example.citassalon.data.retrofit.WebServices
import javax.inject.Inject

class StaffRepositoryRemote @Inject constructor(private val webServices: WebServices) {

    suspend fun getStaffs() = webServices.getStaff()
}