package com.example.domain.repository

import com.example.domain.AdminAppointmentUiModel
import com.example.domain.state.ApiResult

interface AdminRepository {

    suspend fun getPendingAppointmentsForAdmin(): ApiResult<List<AdminAppointmentUiModel>>
    suspend fun getTotalBookings(): ApiResult<String>
    suspend fun getRevenue(): ApiResult<Double>
}