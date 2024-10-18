package com.example.citassalon.presentacion.features.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val rememberUser: Boolean = false,
    val isEmptyFields: Boolean = false,
)