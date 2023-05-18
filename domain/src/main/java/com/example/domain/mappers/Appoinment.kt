package com.example.domain.mappers

import com.example.domain.entities.local.AppointmentObject
import com.example.domain.entities.remote.AppointmentResponse
import com.example.domain.entities.remote.firebase.AppointmentFirebase

fun AppointmentResponse.toAppointmentRemote(idAppointment: String): AppointmentFirebase {
    val establishment = this.establecimiento
    val employee = this.empleado
    val service = this.servicio
    val date = this.fecha
    val hour = this.time
    val total = this.idUser
    return AppointmentFirebase(idAppointment, establishment, employee, service, date, hour, total)
}

fun AppointmentFirebase.toAppointmentResponse(): AppointmentResponse {
    val establishment = this.establishment
    val employee = this.employee
    val service = this.service
    val date = this.date
    val hour = this.hour
    val total = this.total
    return AppointmentResponse(establishment, employee, service, date, hour, total)
}

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


