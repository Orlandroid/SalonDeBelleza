package com.example.domain.loyalty

enum class LoyaltyTier(val minPoints: Int) {
    BRONZE(0),
    SILVER(500),
    GOLD(1000);

    companion object {
        fun fromPoints(points: Int): LoyaltyTier {
            return entries.findLast { points >= it.minPoints } ?: BRONZE
        }
    }
}
