package com.example.citassalon.presentacion.features.app_navigation

import kotlinx.serialization.Serializable

sealed class AppNavigationRoutes {
    @Serializable
    data object AuthNavigationRoute

    @Serializable
    data object ScheduleNavigationRoute

    @Serializable
    data object InfoNavigationRoute

    @Serializable
    data object ProfileNavigationRoute
}