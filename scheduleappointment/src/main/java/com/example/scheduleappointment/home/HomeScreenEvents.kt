package com.example.scheduleappointment.home


sealed class HomeScreenEvents {
    object NavigateToInfoNavigationFlow : HomeScreenEvents()
    object NavigateToChoseBranch : HomeScreenEvents()
    object NavigateToProfile : HomeScreenEvents()
    object OnCloseScreen : HomeScreenEvents()
    object NavigateToWallet : HomeScreenEvents()
}