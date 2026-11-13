package com.example.data.remote.model

import com.example.domain.Appointment

fun AppointmentFirebaseDto.toDomain(): Appointment = Appointment(
    id = idAppointment,
    idAppointment = idAppointment,
    branch = establishment,
    employee = employee,
    employeeId = employeeId,
    service = service,
    date = date,
    hour = hour,
    total = total,
    clientName = clientName,
    status = status
)

fun Appointment.toDto(): AppointmentFirebaseDto = AppointmentFirebaseDto(
    idAppointment = idAppointment.ifEmpty { id },
    establishment = branch,
    employee = employee,
    employeeId = employeeId,
    service = service,
    date = date,
    hour = hour,
    total = total,
    clientName = clientName,
    status = status
)
