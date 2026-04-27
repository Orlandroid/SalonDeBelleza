package com.example.domain.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Staff(
    val name: String,
    val gender: String,
    val rating: Float
) : Parcelable
