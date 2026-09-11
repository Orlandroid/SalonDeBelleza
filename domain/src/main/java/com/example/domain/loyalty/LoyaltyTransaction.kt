package com.example.domain.loyalty

import java.util.UUID

data class LoyaltyTransaction(
    val id: String = UUID.randomUUID().toString(),
    val points: Int = 0,
    val type: LoyaltyTransactionType = LoyaltyTransactionType.APPOINTMENT_EARNED,
    val sourceId: String = "",
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)


enum class LoyaltyTransactionType {
    APPOINTMENT_EARNED,
    REWARD_REDEEMED
}