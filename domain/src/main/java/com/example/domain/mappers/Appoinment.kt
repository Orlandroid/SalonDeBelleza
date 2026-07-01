package com.example.domain.mappers

import com.example.domain.entities.local.AppointmentObject
import com.example.domain.perfil.AppointmentFirebase


fun AppointmentFirebase.toAppointmentObject(): AppointmentObject {
    val idAppointment = this.idAppointment
    val establishment = this.establishment
    val employee = this.employee
    val service = this.service
    val date = this.date
    val hour = this.hour
    val total = this.total
    return AppointmentObject(idAppointment, establishment, employee, service, date, hour, total)
}
