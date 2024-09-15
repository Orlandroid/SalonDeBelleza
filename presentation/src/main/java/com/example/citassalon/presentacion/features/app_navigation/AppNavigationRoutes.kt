package com.example.citassalon.presentacion.features.app_navigation

sealed class AppNavigationRoutes(val route: String) {
    data object AuthNavigation : AppNavigationRoutes("auth_navigation")
    data object ScheduleNavigation : AppNavigationRoutes("schedule_appointment_navigation")
    data object InfoNavigation : AppNavigationRoutes("info_navigation")
    data object ProfileNavigation : AppNavigationRoutes("profile_navigation")
}