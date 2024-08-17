package com.example.citassalon.presentacion.features.login

sealed class LoginUiEffect {
    data object NavigateToHomeScreen : LoginActions()
}