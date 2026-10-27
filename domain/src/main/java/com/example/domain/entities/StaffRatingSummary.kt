package com.example.domain.entities

data class StaffRatingSummary(
    val averageRating: Double = 0.0,
    val totalReviews: Int = 0,
    val recentComments: List<Review> = emptyList()
)
