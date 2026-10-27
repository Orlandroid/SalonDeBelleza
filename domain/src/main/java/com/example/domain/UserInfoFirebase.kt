package com.example.domain

data class UserInfoFirebase(
    val birthDay: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val phone: String = "",
    val role: String = "CUSTOMER"
)
