package com.example.citassalon.presentacion.features.auth.login

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val rememberUserName: Boolean = false,
    val isLoading: Boolean = false,
    val showPassword: Boolean = false,
    val showDialogForgetPassword: Boolean = false,
    val isButtonLoginEnable: Boolean = false,
    val showErrorPassword: Boolean = false,
    val showErrorUserName: Boolean = false,
)