package com.example.citassalon.presentacion.features.auth

sealed class AuthScreens(val route: String) {
    data object Splash : AuthScreens("splash")
    data object Login : AuthScreens("login")
    data object SingUp : AuthScreens("sing_up")
}