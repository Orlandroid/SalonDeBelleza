package com.example.data.remote.appointments

import com.example.domain.entities.local.AppointmentObject
import com.example.domain.entities.remote.Service
import com.example.domain.entities.remote.Staff
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.perfil.Appointment
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.state.ApiResult

interface AppointmentsRepository {

    suspend fun deleteAppointment(idAppointment: String): ApiResult<Any>

    suspend fun getAppointments(): ApiResult<List<Appointment>>

    suspend fun getSingleAppointment(appointmentId: String): ApiResult<AppointmentObject>

    suspend fun saveAppointment(appointment: AppointmentFirebase): ApiResult<Any>

    suspend fun getBranches(): ApiResult<List<NegoInfo>>

    suspend fun getStaffs(): ApiResult<List<Staff>>

    suspend fun getServices(): ApiResult<List<Service>>

}