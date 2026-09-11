package com.example.domain.use_cases.loyalty

import com.example.domain.loyalty.LoyaltyTier
import com.example.domain.loyalty.LoyaltyTransaction
import com.example.domain.loyalty.LoyaltyTransactionType
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import javax.inject.Inject

class EarnPointsUseCase @Inject constructor(
    private val repository: LoyaltyRepository
) {
    suspend operator fun invoke(
        userId: String,
        pointsEarned: Int,
        appointmentId: String
    ): ApiResult<Unit> {

        val result = repository.getLoyalty(userId)
        if (result.isError()) {
            return ApiResult.Error(result.getErrorMessage())
        }

        val current = result.getContent()


        val newBalance = current.balance + pointsEarned
        val newLifetime = current.lifetimePoints + pointsEarned
        val newTier = LoyaltyTier.fromPoints(newLifetime)


        val updatedLoyalty = current.copy(
            balance = newBalance,
            lifetimePoints = newLifetime,
            tier = newTier
        )


        val transaction = LoyaltyTransaction(
            points = pointsEarned,
            type = LoyaltyTransactionType.APPOINTMENT_EARNED,
            sourceId = appointmentId,
            description = "Points earned for completing an appointment",
            createdAt = System.currentTimeMillis()
        )

        repository.addLoyaltyTransaction(userId, transaction)


        return repository.updateLoyalty(updatedLoyalty)
    }
}