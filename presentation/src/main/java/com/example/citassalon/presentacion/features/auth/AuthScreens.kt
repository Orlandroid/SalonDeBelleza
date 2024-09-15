package com.example.citassalon.presentacion.features.auth

sealed class AuthScreens(val route: String) {
    data object SplashScreen : AuthScreens("splash_screen")
    data object LoginScreen : AuthScreens("login")
    data object SingUpScreen : AuthScreens("sing_up")
}