package com.example.domain.entities

import com.example.domain.use_cases.GetUserInfoUseCase.UserSessionStatus

data class UserProfile(
    val name: String,
    val email: String,
    val uid: String,
    val phone: String,
    val money: String,
    val image: String?,
    val sessionStatus: UserSessionStatus
)