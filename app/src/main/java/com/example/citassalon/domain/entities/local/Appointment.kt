package com.example.citassalon.domain.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable

@kotlinx.parcelize.Parcelize
@Entity
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val establecimeinto: String,
    val empleado: String,
    val servicio: String,
    val fecha: String,
    val hora: String,
    val total: String
):Parcelable
