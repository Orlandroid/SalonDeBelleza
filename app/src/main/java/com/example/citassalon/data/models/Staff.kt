package com.example.citassalon.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Staff(val image: Int, val name: String, val evaluation: Float) : Parcelable