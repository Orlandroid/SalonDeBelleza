package com.example.data.remote.model

import com.example.model.state.models.AppointmentStatus
import java.util.UUID

data class AppointmentFirebaseDto(
    val idAppointment: String = UUID.randomUUID().toString(),
    val establishment: String = "",
    val employee: String = "",
    val employeeId: String = "",
    val service: String = "",
    val date: String = "",
    val hour: String = "",
    val total: String = "",
    val clientName: String = "",
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED
)
