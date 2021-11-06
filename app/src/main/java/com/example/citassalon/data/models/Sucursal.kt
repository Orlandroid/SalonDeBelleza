package com.example.citassalon.data.models

import com.google.gson.annotations.SerializedName

data class Sucursal(
    val nombre: String,
    val lat: String,
    @SerializedName("lon")
    val long: String
)
