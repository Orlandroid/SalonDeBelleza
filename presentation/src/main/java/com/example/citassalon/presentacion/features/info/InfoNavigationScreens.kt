package com.example.citassalon.presentacion.features.info

sealed class InfoNavigationScreens(val route: String) {
    data object Establishing : InfoNavigationScreens("establishing")
    data object Stores : InfoNavigationScreens("stores")
    data object Branches : InfoNavigationScreens("branches")
    data object Categories : InfoNavigationScreens("categories")
    data object Products : InfoNavigationScreens("products")
}