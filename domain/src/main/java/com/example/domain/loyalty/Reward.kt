package com.example.domain.loyalty

data class Reward(
    val id: String = "",
    val name: String = "",
    val pointsRequired: Int = 0,
    val discountPercentage: Int = 0
)
