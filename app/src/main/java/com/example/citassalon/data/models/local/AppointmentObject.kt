package com.example.citassalon.data.models.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentObject(
    var idAppointment: String = "",
    var establishment: String = "",
    var employee: String = "",
    var service: String = "",
    var date: String = "",
    var hour: String = "",
    var total: String = "",
) : Parcelable
