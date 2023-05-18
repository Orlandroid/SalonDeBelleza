package com.example.domain.entities.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

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
