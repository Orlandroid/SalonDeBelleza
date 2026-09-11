package com.example.core.navigation.loyalty

import kotlinx.serialization.Serializable


sealed class LoyaltyNavigationRoutes {
    @Serializable
    data object RewardsRoute : LoyaltyNavigationRoutes()
}