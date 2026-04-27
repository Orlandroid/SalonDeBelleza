package com.example.domain.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable

@kotlinx.parcelize.Parcelize
@Entity
data class AppointmentDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val establishment: String,
    val employee: String,
    val service: String,
    val date: String,
    val time: String,
    val total: String
):Parcelable
