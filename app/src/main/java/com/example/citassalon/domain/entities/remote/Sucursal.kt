package com.example.citassalon.domain.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Sucursal(
    val name: String,
    val lat: String,
    val long: String
) : Parcelable
