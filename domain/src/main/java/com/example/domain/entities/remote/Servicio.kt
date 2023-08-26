package com.example.domain.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Servicio(val name: String, val precio: Int, var isSelect: Boolean = false) : Parcelable