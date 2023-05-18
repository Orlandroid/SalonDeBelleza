package com.example.domain.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Staff(
    val nombre: String,
    val sexo: String,
    val valoracion: Float
) : Parcelable
