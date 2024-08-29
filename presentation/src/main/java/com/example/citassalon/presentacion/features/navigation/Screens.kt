package com.example.citassalon.presentacion.features.navigation

sealed class Screens(val route: String) {
    data object SplashScreen : Screens("splash_screen")
    data object LoginScreen : Screens("login")
    data object SingUpScreen : Screens("sing_up")
    data object HomeScreen : Screens("home")
    data object ProfileScreen : Screens("profile")
    data object UserProfileScreen : Screens("user_profile")
}