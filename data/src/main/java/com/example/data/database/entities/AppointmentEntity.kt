package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val establishment: String,
    val employee: String,
    val service: String,
    val date: String,
    val time: String,
    val total: String
)