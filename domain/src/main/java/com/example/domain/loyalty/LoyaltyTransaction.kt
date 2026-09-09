package com.example.domain.loyalty

data class LoyaltyTransaction(
    val id: String = "",
    val points: Int = 0,
    val type: String = "", // e.g., "APPOINTMENT_EARNED", "REWARD_REDEEMED"
    val sourceId: String = "", // Appointment ID or Reward ID
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
