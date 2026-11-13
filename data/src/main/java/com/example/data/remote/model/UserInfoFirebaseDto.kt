package com.example.data.remote.model

data class UserInfoFirebaseDto(
    val birthDay: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val phone: String = "",
    val role: String = "CUSTOMER"
)
