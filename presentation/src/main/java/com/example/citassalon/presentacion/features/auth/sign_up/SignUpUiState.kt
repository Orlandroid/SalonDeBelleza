package com.example.citassalon.presentacion.features.auth.sign_up

data class SignUpUiState(
    var showErrorPhone: Boolean = false,
    var showErrorEmail: Boolean = false,
    var showErrorPassword: Boolean = false
)
