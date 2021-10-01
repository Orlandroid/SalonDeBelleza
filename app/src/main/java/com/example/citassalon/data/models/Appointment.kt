package com.example.citassalon.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

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
)
