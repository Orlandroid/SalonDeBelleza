package com.example.citassalon.data.mappers

import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.data.models.local.AppointmentObject
import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.data.models.remote.Appointment as AppointmentRemote

fun AppointmentResponse.toAppointmentRemote(idAppointment: String): AppointmentRemote {
    val establishment = this.establecimiento
    val employee = this.empleado
    val service = this.servicio
    val date = this.fecha
    val hour = this.time
    val total = this.idUser
    return AppointmentRemote(idAppointment, establishment, employee, service, date, hour, total)
}

fun AppointmentRemote.toAppointmentResponse(): AppointmentResponse {
    val establishment = this.establishment
    val employee = this.employee
    val service = this.service
    val date = this.date
    val hour = this.hour
    val total = this.total
    return AppointmentResponse(establishment, employee, service, date, hour, total)
}

fun AppointmentRemote.toAppointmentObject(): AppointmentObject {
    val idAppointment = this.idAppointment
    val establishment = this.establishment
    val employee = this.employee
    val service = this.service
    val date = this.date
    val hour = this.hour
    val total = this.total
    return AppointmentObject(idAppointment,establishment,employee, service, date, hour, total)
}

