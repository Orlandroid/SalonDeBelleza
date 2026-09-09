package com.example.domain.use_cases.loyalty

import com.example.domain.loyalty.LoyaltyTier
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import javax.inject.Inject

class EarnPointsUseCase @Inject constructor(
    private val repository: LoyaltyRepository
) {
    suspend operator fun invoke(userId: String, pointsEarned: Int): ApiResult<Unit> {
        // 1. Get current state
        val result = repository.getLoyalty(userId)
        if (result is ApiResult.Error) return ApiResult.Error(result.error)

        val current = (result as ApiResult.Success).result

        // 2. Calculate new values
        val newBalance = current.balance + pointsEarned
        val newLifetime = current.lifetimePoints + pointsEarned
        val newTier = LoyaltyTier.fromPoints(newLifetime)

        // 3. Save back to Firebase
        val updatedLoyalty = current.copy(
            balance = newBalance,
            lifetimePoints = newLifetime,
            tier = newTier
        )

        return repository.updateLoyalty(updatedLoyalty)
    }
}