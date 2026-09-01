package com.example.domain.perfil

import java.util.UUID


data class AppointmentFirebase(
    var idAppointment: String = UUID.randomUUID().toString(),
    var establishment: String = "",
    var employee: String = "",
    var service: String = "",
    var date: String = "",
    var hour: String = "",
    var total: String = "",
)