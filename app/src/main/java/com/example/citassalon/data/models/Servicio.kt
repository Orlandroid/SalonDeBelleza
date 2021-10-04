package com.example.citassalon.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Servicio(val name: String, val precio: Int) : Parcelable