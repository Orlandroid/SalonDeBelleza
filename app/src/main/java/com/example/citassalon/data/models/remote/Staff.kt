package com.example.citassalon.data.models.remote

import android.os.Parcelable
import com.example.citassalon.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class Staff(
    val nombre: String,
    val sexo: String,
    val valoracion: Float
) : Parcelable {
    fun getResourceImage(): Int =
        when (sexo) {
            "hombre" -> R.drawable.image_18
            else -> R.drawable.image_15
        }
}
