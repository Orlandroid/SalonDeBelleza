package com.example.domain.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
)