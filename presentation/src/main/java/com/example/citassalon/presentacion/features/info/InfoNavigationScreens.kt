package com.example.citassalon.presentacion.features.info

sealed class InfoNavigationScreens(val route: String) {
    data object Establishing : InfoNavigationScreens("establishing_screen")
}