package com.example.domain.repository

import com.example.domain.Appointment
import com.example.domain.AppointmentFirebase
import com.example.domain.entities.local.AppointmentObject
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.entities.remote.migration.Service
import com.example.domain.state.ApiResult
import com.example.domain.entities.remote.migration.Staff

interface AppointmentsRepository {

    suspend fun deleteAppointment(idAppointment: String): ApiResult<Any>

    suspend fun getAppointments(): ApiResult<List<Appointment>>

    suspend fun getSingleAppointment(appointmentId: String): ApiResult<AppointmentObject>

    suspend fun getAppointmentById(appointmentId: String): ApiResult<AppointmentFirebase>

    suspend fun saveAppointment(appointment: AppointmentFirebase): ApiResult<Any>

    suspend fun updateAppointment(
        appointmentId: String,
        appointment: AppointmentFirebase
    ): ApiResult<Unit>

    suspend fun getBranches(): ApiResult<List<NegoInfo>>

    suspend fun getStaffs(): ApiResult<List<Staff>>

    suspend fun getServices(): ApiResult<List<Service>>

    suspend fun findStaff(
        branchName: String,
        staffName: String
    ): ApiResult<Pair<String, Staff?>>

    suspend fun getBookedSlots(
        branchName: String,
        date: String,
        staffName: String
    ): ApiResult<List<String>>

    suspend fun bookMasterSchedule(
        branchName: String,
        date: String,
        staffName: String,
        time: String,
        appointmentId: String
    ): ApiResult<Unit>

}