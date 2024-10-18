package com.example.citassalon.presentacion.features.profile

import com.example.domain.entities.local.AppointmentObject
import kotlinx.serialization.Serializable


sealed class ProfileNavigationScreen {
    @Serializable
    data object ProfileRoute

    @Serializable
    data object UserProfileRoute

    @Serializable
    data object AppointmentHistoryRoute

    @Serializable
    data class HistoryDetailRoute(val appointment: AppointmentObject)

    @Serializable
    data object ContactsRoute

    @Serializable
    data object TermsAndConditionsRoute
}

