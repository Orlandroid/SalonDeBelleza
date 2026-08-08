package com.example.core.schedule

import kotlinx.serialization.Serializable

sealed class ScheduleAppointmentScreens {
    @Serializable
    data object HomeRoute : ScheduleAppointmentScreens()

    @Serializable
    data object ChoseBranchRoute : ScheduleAppointmentScreens()

    @Serializable
    data object ScheduleStaffRoute : ScheduleAppointmentScreens()

    @Serializable
    data object DetailStaffRoute : ScheduleAppointmentScreens()

    @Serializable
    data object ServicesRoute : ScheduleAppointmentScreens()

    @Serializable
    data object ScheduleRoute : ScheduleAppointmentScreens()

    @Serializable
    data object ScheduleConfirmationRoute : ScheduleAppointmentScreens()

    @Serializable
    data object AppointmentScheduledRoute : ScheduleAppointmentScreens()
}

