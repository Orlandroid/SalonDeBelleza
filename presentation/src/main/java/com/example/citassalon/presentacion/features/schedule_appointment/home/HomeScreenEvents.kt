package com.example.citassalon.presentacion.features.schedule_appointment.home


sealed class HomeScreenEvents {
    object NavigateToInfoNavigationFlow : HomeScreenEvents()
    object NavigateToChoseBranch : HomeScreenEvents()
    object NavigateToProfile : HomeScreenEvents()
    object OnCloseScreen : HomeScreenEvents()
}