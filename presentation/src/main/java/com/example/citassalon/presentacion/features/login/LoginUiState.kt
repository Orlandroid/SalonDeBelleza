package com.example.citassalon.presentacion.features.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val userName: String = "",
    val password: String = "",
    val rememberUser: Boolean = false
)