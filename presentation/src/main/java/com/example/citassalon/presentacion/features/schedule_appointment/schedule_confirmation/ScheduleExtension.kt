package com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation

import com.example.citassalon.presentacion.features.schedule_appointment.FlowMainViewModel
import com.example.domain.perfil.AppointmentFirebase
import java.util.UUID

fun FlowMainViewModel.getAppointmentFirebase(): AppointmentFirebase {
    return AppointmentFirebase(
        idAppointment = UUID.randomUUID().toString(),
        establishment = sucursal.name,
        employee = currentStaff.nombre,
        service = listOfServices[0].name,
        date = dateAppointment,
        hour = hourAppointment,
        total = listOfServices[0].precio.toString(),
    )
}