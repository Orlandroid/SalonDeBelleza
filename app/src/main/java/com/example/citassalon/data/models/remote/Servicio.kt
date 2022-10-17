package com.example.citassalon.data.models.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Servicio(val name: String, val precio: Int, var isSelect: Boolean = false) : Parcelable