package com.example.domain.use_cases


import com.example.domain.entities.Review
import com.example.domain.repository.ReviewRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import javax.inject.Inject

data class StaffRatingSummary(
    val averageRating: Double = 0.0,
    val totalReviews: Int = 0,
    val recentComments: List<Review> = emptyList()
)

class GetStaffRatingUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(staffId: String): ApiResult<StaffRatingSummary> {
        val result = reviewRepository.getStaffReviews(staffId)

        if (result is ApiResult.Error) return ApiResult.Error(result.error)

        val reviews = result.getContent()

        if (reviews.isEmpty()) {
            return ApiResult.Success(StaffRatingSummary())
        }

        val average = reviews.map { it.rating }.average()

        return ApiResult.Success(
            StaffRatingSummary(
                averageRating = average,
                totalReviews = reviews.size,
                recentComments = reviews.take(5)
            )
        )
    }
}