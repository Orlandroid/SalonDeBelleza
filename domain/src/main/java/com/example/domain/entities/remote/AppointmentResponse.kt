package com.example.domain.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentResponse(
    val establishment: String,
    val employee: String,
    val service: String,
    val time: String,
    val date: String,
    val userId: String
) : Parcelable