package com.example.domain.loyalty

import java.util.UUID

data class PromotionCode(
    val id: String = UUID.randomUUID().toString(),
    val code: String = "",
    val rewardId: String = "",
    val discountPercentage: Int = 0,
    val used: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)