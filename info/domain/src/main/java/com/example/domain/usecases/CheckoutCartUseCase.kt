package com.example.domain.usecases

import com.example.domain.Product
import com.example.domain.loyalty.LoyaltyTransactionType
import com.example.domain.loyalty.PromotionCode
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.loyalty.EarnPointsUseCase
import com.example.model.state.ApiResult
import com.example.model.state.getErrorMessage
import com.example.model.state.getResultOrNull
import com.example.model.state.isError
import com.example.model.state.models.TransactionType
import java.util.UUID
import javax.inject.Inject

class CheckoutCartUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val purchaseProductsUseCase: PurchaseProductsUseCase,
    private val loyaltyRepository: LoyaltyRepository,
    private val earnPointsUseCase: EarnPointsUseCase
) {
    suspend operator fun invoke(
        products: List<Product>,
        appliedPromo: PromotionCode?
    ): ApiResult<Unit> {
        val userId = userRepository.getUser().getResultOrNull()?.uid
            ?: return ApiResult.Error("User not authenticated")

        if (products.isEmpty()) {
            return ApiResult.Error("Cart is empty")
        }

        val originalPrice = calculateTotalOfProducts(products)
        val finalPrice = calculateFinalPrice(originalPrice, appliedPromo)
        val description = buildDescription(products)

        val purchaseResult = purchaseProductsUseCase(
            amount = finalPrice,
            transactionType = TransactionType.MARKETPLACE_PURCHASE,
            description = description
        )

        if (purchaseResult.isError()) {
            return ApiResult.Error(purchaseResult.getErrorMessage() ?: "Purchase failed")
        }

        if (appliedPromo != null) {
            loyaltyRepository.usePromotionCode(userId = userId, promoId = appliedPromo.id)
        }

        earnPointsUseCase(
            userId = userId,
            pointsEarned = finalPrice.toInt(),
            sourceId = UUID.randomUUID().toString(),
            description = "Marketplace purchase: $description",
            type = LoyaltyTransactionType.MARKETPLACE_PURCHASE
        )

        return ApiResult.Success(Unit)
    }

    private fun calculateTotalOfProducts(products: List<Product>): Long =
        products.sumOf { it.price * it.quantity }

    private fun calculateFinalPrice(originalPrice: Long, promo: PromotionCode?): Long {
        if (promo == null) return originalPrice
        val discount = originalPrice * (promo.discountPercentage.toDouble() / PERCENTAGE_BASE)
        return (originalPrice - discount).toLong().coerceAtLeast(0L)
    }

    private fun buildDescription(products: List<Product>): String =
        when (products.size) {
            1 -> products.first().title
            2 -> products.joinToString(" + ") { it.title }
            else -> "${products.first().title} + ${products.size - 1} more"
        }

    private companion object {
        const val PERCENTAGE_BASE = 100.0
    }
}
