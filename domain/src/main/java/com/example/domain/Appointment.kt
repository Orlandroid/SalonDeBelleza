package com.example.domain

data class Appointment(
    val service: String,
    val branch: String,
    val id: String,
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED
)

fun AppointmentFirebase.toAppointment(): Appointment {
    return Appointment(
        service = service,
        branch = establishment,
        id = idAppointment,
        status = status
    )
}
