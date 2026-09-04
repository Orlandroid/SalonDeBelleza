package com.example.domain.entities

import com.example.domain.UserSessionStatus

data class UserProfile(
    val name: String,
    val email: String,
    val uid: String,
    val phone: String,
    val money: Long,
    val image: String?,
    val sessionStatus: UserSessionStatus
)