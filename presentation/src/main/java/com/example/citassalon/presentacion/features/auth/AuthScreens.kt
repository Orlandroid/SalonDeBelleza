package com.example.citassalon.presentacion.features.auth

import kotlinx.serialization.Serializable

sealed class AuthScreens {
    @Serializable
    data object SplashRoute

    @Serializable
    data object LoginRoute

    @Serializable
    data object SingUpRoute
}