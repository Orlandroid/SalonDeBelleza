package com.example.core.navigation

import kotlinx.serialization.Serializable

sealed class AppNavigationRoutes {

    @Serializable
    data object AuthNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object ScheduleNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object InfoNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object ProfileNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object WalletNavigationRoute : AppNavigationRoutes()
}