package com.example.citassalon.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "staff")
data class Staff(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Int,
    val name: String,
    val evaluation: Float
) : Parcelable