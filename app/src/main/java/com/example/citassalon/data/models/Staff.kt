package com.example.citassalon.data.models

import android.os.Parcelable
import com.example.citassalon.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class Staff(
    val id: Int = 0,
    val image: Int = R.drawable.image_20,
    val nombre: String,
    val sexo: String,
    val valoracion: Float
) : Parcelable
