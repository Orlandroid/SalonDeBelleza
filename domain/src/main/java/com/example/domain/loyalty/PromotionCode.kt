package com.example.domain.loyalty

data class PromotionCode(
    val id: String = "",
    val code: String = "",
    val rewardId: String = "",
    val discountPercentage: Int = 0,
    val used: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)