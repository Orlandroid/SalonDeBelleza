package com.example.domain.usecases


import com.example.domain.entities.StaffRatingSummary
import com.example.domain.repository.ReviewRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import javax.inject.Inject

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