package com.example.domain.repository

import com.example.domain.loyalty.Loyalty
import com.example.domain.state.ApiResult

interface LoyaltyRepository {
    suspend fun initializeLoyalty(userId: String): ApiResult<Unit>
    suspend fun getLoyalty(userId: String): ApiResult<Loyalty>
    suspend fun updateLoyalty(loyalty: Loyalty): ApiResult<Unit>
}