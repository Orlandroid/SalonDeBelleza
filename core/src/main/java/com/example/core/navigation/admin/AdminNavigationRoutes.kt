package com.example.core.navigation.admin

import kotlinx.serialization.Serializable

sealed class AdminNavigationRoutes {
    @Serializable
    data object AdminDashboardRoute : AdminNavigationRoutes()
}
