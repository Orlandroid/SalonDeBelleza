package com.example.domain.entities.local

import com.example.model.state.models.AppointmentStatus
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentObject(
    val idAppointment: String = "",
    val establishment: String = "",
    val employee: String = "",
    val service: String = "",
    val date: String = "",
    val hour: String = "",
    val total: String = "",
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED
)
