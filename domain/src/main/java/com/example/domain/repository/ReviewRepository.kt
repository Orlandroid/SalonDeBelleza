package com.example.domain.repository

import com.example.domain.entities.Review
import com.example.domain.state.ApiResult

interface ReviewRepository {
    suspend fun addReview(review: Review): ApiResult<Unit>
    suspend fun getStaffReviews(staffId: String): ApiResult<List<Review>>
}