package com.example.domain.usecases.loyalty


import com.example.domain.loyalty.LoyaltyTransaction
import com.example.domain.loyalty.LoyaltyTransactionType
import com.example.domain.loyalty.PromotionCode
import com.example.domain.loyalty.Reward
import com.example.domain.repository.LoyaltyRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.isError
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