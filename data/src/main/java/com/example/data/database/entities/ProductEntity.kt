package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Long,
    val description: String,
    val image: String,
    val rate: Double
)