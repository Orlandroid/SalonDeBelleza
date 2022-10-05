package com.example.citassalon.data.models.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Sucursal(
    val name: String,
    val lat: String,
    val long: String
) : Parcelable
