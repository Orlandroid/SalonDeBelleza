package com.example.domain.perfil

data class Appointment(
    val service: String,
    val branch: String,
    val id: String
)

fun AppointmentFirebase.toAppointment(): Appointment {
    return Appointment(service = service, branch = establishment, id = idAppointment)
}


fun List<AppointmentFirebase>.toAppointmentList() = map { it.toAppointment() }

