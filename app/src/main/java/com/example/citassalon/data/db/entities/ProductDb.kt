package com.example.citassalon.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rate: Double,
    val count: Int
)