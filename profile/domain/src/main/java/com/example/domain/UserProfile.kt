package com.example.domain

import com.example.domain.loyalty.Loyalty
import com.example.domain.loyalty.PromotionCode
import com.example.model.state.models.UserRole

data class UserProfile(
    val name: String,
    val email: String,
    val uid: String,
    val phone: String,
    val money: Long,
    val image: String?,
    val sessionStatus: UserSessionStatus,
    val loyalty: Loyalty? = null,
    val coupons: List<PromotionCode> = emptyList(),
    val role: UserRole = UserRole.CUSTOMER
)
