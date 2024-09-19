package com.example.citassalon.presentacion.features.profile


sealed class ProfileNavigationScreen(val route: String) {
    data object Profile : ProfileNavigationScreen("profile")
    data object UserProfile : ProfileNavigationScreen("user_profile")
    data object AppointmentHistory : ProfileNavigationScreen("history")
    data object Contacts : ProfileNavigationScreen("history")
    data object TermsAndConditions : ProfileNavigationScreen("history")
}

