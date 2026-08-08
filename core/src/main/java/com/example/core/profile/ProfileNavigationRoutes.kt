package com.example.core.profile

import kotlinx.serialization.Serializable


sealed class ProfileNavigationRoutes {
    @Serializable
    data object ProfileRoute : ProfileNavigationRoutes()

    @Serializable
    data object UserProfileRoute : ProfileNavigationRoutes()

    @Serializable
    data object AppointmentHistoryRoute : ProfileNavigationRoutes()

    @Serializable
    data class HistoryDetailRoute(val appointmentId: String) : ProfileNavigationRoutes()

    @Serializable
    data object ContactsRoute : ProfileNavigationRoutes()

    @Serializable
    data object TermsAndConditionsRoute : ProfileNavigationRoutes()
}

