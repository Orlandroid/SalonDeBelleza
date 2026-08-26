package com.example.core.navigation.schedule

import kotlinx.serialization.Serializable

sealed class ScheduleNavigationRoutes {
    @Serializable
    data object HomeRoute : ScheduleNavigationRoutes()

    @Serializable
    data object ChoseBranchRoute : ScheduleNavigationRoutes()

    @Serializable
    data object ScheduleStaffRoute : ScheduleNavigationRoutes()

    @Serializable
    data object DetailStaffRoute : ScheduleNavigationRoutes()

    @Serializable
    data object ServicesRoute : ScheduleNavigationRoutes()

    @Serializable
    data object ScheduleRoute : ScheduleNavigationRoutes()

    @Serializable
    data object ScheduleConfirmationRoute : ScheduleNavigationRoutes()

    @Serializable
    data object SuccessScheduleRoute : ScheduleNavigationRoutes()

    @Serializable
    data object AppointmentScheduledRoute : ScheduleNavigationRoutes()
}

