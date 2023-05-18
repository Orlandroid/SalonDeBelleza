package com.example.domain.entities.remote


class Appointment {

    var idAppointment: String = ""
    var establishment: String = ""
    var employee: String = ""
    var service: String = ""
    var date: String = ""
    var hour: String = ""
    var total: String = ""

    constructor()

    override fun toString(): String {
        return "$establishment,$employee,$service,$date,$hour,$total"
    }

    constructor(
        idAppointment: String,
        establishment: String,
        employee: String,
        service: String,
        date: String,
        hour: String,
        total: String
    ) {
        this.idAppointment = idAppointment
        this.establishment = establishment
        this.employee = employee
        this.service = service
        this.date = date
        this.hour = hour
        this.total = total
    }

}
