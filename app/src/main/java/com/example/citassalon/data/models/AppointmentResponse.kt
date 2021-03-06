package com.example.citassalon.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentResponse(
    val establecimiento: String,
    val empleado: String,
    val servicio: String,
    val time: String,
    val fecha: String,
    val idUser: String
):Parcelable