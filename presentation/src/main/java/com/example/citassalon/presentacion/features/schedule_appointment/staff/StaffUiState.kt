package com.example.citassalon.presentacion.features.schedule_appointment.staff

import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff

data class StaffUiState(
    val listOfStaffs: List<Staff> = emptyList(),
    val branchName: String = "",
    val currentStaff: Staff? = null,
    val listOfServices: List<Service> = emptyList()
)