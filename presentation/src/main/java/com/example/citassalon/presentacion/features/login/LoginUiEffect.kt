package com.example.citassalon.presentacion.features.login

sealed class LoginUiEffect {
    data object NavigateToHomeScreen : LoginUiEffect()
    data object GoToSignUp : LoginUiEffect()
    data object Idle : LoginUiEffect()
}