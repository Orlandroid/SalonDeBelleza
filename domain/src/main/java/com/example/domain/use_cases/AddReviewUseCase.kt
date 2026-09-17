package com.example.domain.use_cases

import com.example.domain.entities.Review
import com.example.domain.loyalty.LoyaltyTransactionType
import com.example.domain.repository.ReviewRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.isError
import com.example.domain.use_cases.loyalty.EarnPointsUseCase
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val earnPointsUseCase: EarnPointsUseCase
) {
    suspend operator fun invoke(
        userId: String,
        review: Review
    ): ApiResult<Unit> {


        val reviewResult = reviewRepository.addReview(review)
        if (reviewResult.isError()) return reviewResult

        return earnPointsUseCase(
            userId = userId,
            pointsEarned = 50,
            sourceId = review.id,
            description = "Reward for leaving a review",
            type = LoyaltyTransactionType.REVIEW_EARNED
        )
    }
}