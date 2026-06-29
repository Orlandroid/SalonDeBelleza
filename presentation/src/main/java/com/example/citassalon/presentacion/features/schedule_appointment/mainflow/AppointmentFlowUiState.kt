package com.example.citassalon.presentacion.features.schedule_appointment.mainflow

import com.example.citassalon.presentacion.features.extensions.dateFormat
import com.example.citassalon.presentacion.features.extensions.getCurrentDateTime
import com.example.citassalon.presentacion.features.extensions.getInitialTime
import com.example.citassalon.presentacion.features.extensions.toStringFormat
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff

data class AppointmentFlowUiState(
    val listOfStaffs: List<Staff> = emptyList(),
    val branchName: String = "",
    val currentStaff: Staff? = null,
    val listOfServices: List<Service> = emptyList(),
    val dateAppointment: String = getCurrentDateTime().toStringFormat(dateFormat),
    val timeAppointment: String = getInitialTime()
)