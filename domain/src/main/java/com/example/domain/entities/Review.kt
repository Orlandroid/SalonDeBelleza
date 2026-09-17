package com.example.domain.entities

import java.util.UUID

data class Review(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val userName: String = "",
    val staffId: String = "",
    val staffName: String = "",
    val appointmentId: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val createdAt: Long = System.currentTimeMillis()
)