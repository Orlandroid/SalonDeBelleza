package com.example.domain.repository

import com.example.domain.loyalty.Loyalty
import com.example.domain.loyalty.LoyaltyTransaction
import com.example.domain.loyalty.PromotionCode
import com.example.domain.loyalty.Reward
import com.example.domain.state.ApiResult
import kotlinx.coroutines.flow.Flow

interface LoyaltyRepository {
    suspend fun initializeLoyalty(userId: String): ApiResult<Unit>
    suspend fun getLoyalty(userId: String): ApiResult<Loyalty>
    suspend fun updateLoyalty(loyalty: Loyalty): ApiResult<Unit>
    suspend fun addLoyaltyTransaction(userId: String, transaction: LoyaltyTransaction): ApiResult<Unit>
    suspend fun getRewards(): ApiResult<List<Reward>>
    suspend fun addPromotionCode(userId: String, promotionCode: PromotionCode): ApiResult<Unit>
    suspend fun getPromotionCodes(userId: String): ApiResult<List<PromotionCode>>
}
