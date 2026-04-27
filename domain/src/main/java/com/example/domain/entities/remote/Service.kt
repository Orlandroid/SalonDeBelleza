package com.example.domain.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Service(val name: String, val price: Int, var isSelect: Boolean = false) : Parcelable