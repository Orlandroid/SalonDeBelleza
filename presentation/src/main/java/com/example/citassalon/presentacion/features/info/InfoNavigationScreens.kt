package com.example.citassalon.presentacion.features.info

import com.example.citassalon.presentacion.features.info.stores.StoresFragment

sealed class InfoNavigationScreens(val route: String) {
    data object Establishing : InfoNavigationScreens("establishing")
    data object Stores : InfoNavigationScreens("stores")
    data object Branches : InfoNavigationScreens("branches")
    data object Categories : InfoNavigationScreens("categories/{store}") {
        fun navigate(store: StoresFragment.Store) = "$route/$store"
    }
}