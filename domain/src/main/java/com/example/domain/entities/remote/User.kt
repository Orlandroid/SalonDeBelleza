package com.example.domain.entities.remote

import com.example.domain.entities.UserRole

data class User(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val birthDay: String,
    val role: UserRole = UserRole.CUSTOMER
)
