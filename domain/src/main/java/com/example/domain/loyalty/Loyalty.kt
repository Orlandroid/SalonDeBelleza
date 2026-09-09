package com.example.domain.loyalty

data class Loyalty(
    val userId: String = "",
    val balance: Int = 0,
    val lifetimePoints: Int = 0,
    val tier: LoyaltyTier = LoyaltyTier.BRONZE
)
