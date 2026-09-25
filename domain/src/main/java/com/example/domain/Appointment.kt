package com.example.domain

data class Appointment(
    val idAppointment: String,
    val service: String,
    val branch: String,
    val employee: String,
    val employeeId: String,
    val date: String,
    val hour: String,
    val id: String,
    val clientName: String,
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED
)

fun AppointmentFirebase.toAppointment(): Appointment {
    return Appointment(
        service = service,
        branch = establishment,
        id = idAppointment,
        status = status,
        idAppointment = idAppointment,
        employee = employee,
        employeeId = employeeId,
        date = date,
        hour = hour,
        clientName = clientName
    )
}
