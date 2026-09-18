package com.example.domain

import java.util.UUID

data class AppointmentFirebase(
    val idAppointment: String = UUID.randomUUID().toString(),
    val establishment: String = "",
    val employee: String = "",
    val employeeId: String = "",
    val service: String = "",
    val date: String = "",
    val hour: String = "",
    val total: String = "",
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED
)
