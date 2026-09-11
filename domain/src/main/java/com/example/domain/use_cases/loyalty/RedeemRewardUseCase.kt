package com.example.domain.use_cases.loyalty


import com.example.domain.loyalty.*
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import java.util.UUID
import javax.inject.Inject


class RedeemRewardUseCase @Inject constructor(
    private val repository: LoyaltyRepository
) {
    suspend operator fun invoke(userId: String, reward: Reward): ApiResult<PromotionCode> {

        val loyaltyResult = repository.getLoyalty(userId)
        if (loyaltyResult.isError()) return ApiResult.Error(loyaltyResult.getErrorMessage())

        val currentLoyalty = loyaltyResult.getContent()

        if (currentLoyalty.balance < reward.pointsRequired) {
            return ApiResult.Error("Insufficient points")
        }

        val updatedLoyalty = currentLoyalty.copy(
            balance = currentLoyalty.balance - reward.pointsRequired
        )

        val transaction = LoyaltyTransaction(
            points = -reward.pointsRequired,
            type = LoyaltyTransactionType.REWARD_REDEEMED,
            sourceId = reward.id,
            description = "Reward redemption: ${reward.name}"
        )


        val promoCode = PromotionCode(
            code = "BRANCH-" + UUID.randomUUID().toString().take(5).uppercase(),
            rewardId = reward.id,
            discountPercentage = reward.discountPercentage
        )


        repository.updateLoyalty(updatedLoyalty)
        repository.addLoyaltyTransaction(userId, transaction)
        repository.addPromotionCode(userId, promoCode)

        return ApiResult.Success(promoCode)
    }
}