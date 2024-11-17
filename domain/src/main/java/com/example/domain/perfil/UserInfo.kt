package com.example.domain.perfil


data class UserProfileResponse(
    val isUserSessionActive: Boolean,
    val userInfo: List<UserInfo>
)

data class UserInfo(
    val title: String = "",
    val value: String = "",
)
