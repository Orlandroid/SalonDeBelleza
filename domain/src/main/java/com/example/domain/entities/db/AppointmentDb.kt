package com.example.domain.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable

@kotlinx.parcelize.Parcelize
@Entity
data class AppointmentDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val establecimeinto: String,
    val empleado: String,
    val servicio: String,
    val fecha: String,
    val hora: String,
    val total: String
):Parcelable
