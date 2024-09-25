package com.example.citassalon.presentacion.features.schedule_appointment

import kotlinx.serialization.Serializable

sealed class ScheduleAppointmentScreens {
    @Serializable
    data object HomeRoute

    @Serializable
    data object ChoseBranchRoute

    @Serializable
    data object ScheduleStaffRoute

    @Serializable
    data object DetailStaffRoute

    @Serializable
    data object ServicesRoute

    @Serializable
    data object ScheduleRoute

    @Serializable
    data object ScheduleConfirmationRoute
}

