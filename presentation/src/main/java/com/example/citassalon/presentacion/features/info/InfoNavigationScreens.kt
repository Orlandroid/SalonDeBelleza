package com.example.citassalon.presentacion.features.info

sealed class InfoNavigationScreens(val route: String) {
    data object EstablishingScreen : InfoNavigationScreens("establishing_screen")
}