package com.example.citassalon.presentacion.features.auth.login

sealed class LoginSideEffects {
    data object NavigateToHomeScreen : LoginSideEffects()
    data object GoToSignUp : LoginSideEffects()
}