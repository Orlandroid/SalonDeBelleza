package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
)