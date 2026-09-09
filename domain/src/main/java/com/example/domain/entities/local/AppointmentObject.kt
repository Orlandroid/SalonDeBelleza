package com.example.domain.entities.local

import com.example.domain.AppointmentStatus
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentObject(
    var idAppointment: String = "",
    var establishment: String = "",
    var employee: String = "",
    var service: String = "",
    var date: String = "",
    var hour: String = "",
    var total: String = "",
    var status: AppointmentStatus = AppointmentStatus.CONFIRMED
)
