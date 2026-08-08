package com.example.core.auth

import kotlinx.serialization.Serializable

sealed class AuthNavigationRoutes {
    @Serializable
    data object SplashRoute : AuthNavigationRoutes()

    @Serializable
    data object LoginRoute : AuthNavigationRoutes()

    @Serializable
    data object SingUpRoute : AuthNavigationRoutes()
}