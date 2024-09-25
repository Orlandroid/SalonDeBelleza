package com.example.citassalon.presentacion.features.info

import kotlinx.serialization.Serializable

sealed class InfoNavigationScreens(val route: String) {
    data object Establishing : InfoNavigationScreens("establishing")
    data object Stores : InfoNavigationScreens("stores")
    data object Branches : InfoNavigationScreens("branches")
    data object Categories : InfoNavigationScreens("categories")
    data object Products : InfoNavigationScreens("products")
    data object Cart : InfoNavigationScreens("cart")
}

@Serializable
data object EstablishingRoute

@Serializable
data object StoresRoute

@Serializable
data object BranchesRoute

@Serializable
data object CategoriesRoute

@Serializable
data object ProductsRoute

@Serializable
data object CartRoute