package com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation

import com.example.citassalon.presentacion.features.schedule_appointment.mainflow.AppointmentFlowViewModel
import com.example.domain.perfil.AppointmentFirebase
import java.util.UUID

fun AppointmentFlowViewModel.getAppointmentFirebase(): AppointmentFirebase {
    return AppointmentFirebase(
        idAppointment = UUID.randomUUID().toString(),
        establishment = staffUiState.value.branchName,
        employee = staffUiState.value.currentStaff?.name.orEmpty(),
        service = staffUiState.value.listOfServices[0].name,
        date = staffUiState.value.dateAppointment,
        hour = staffUiState.value.timeAppointment,
        total = staffUiState.value.listOfServices[0].precio.toString(),
    )
}