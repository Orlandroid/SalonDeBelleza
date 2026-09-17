package com.example.data.remote

import com.example.di.qualifiers.ReviewsRef
import com.example.domain.entities.Review
import com.example.domain.repository.ReviewRepository
import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.database.ktx.getValue

class ReviewRepositoryImpl @Inject constructor(
    @ReviewsRef private val reviewsRef: DatabaseReference
) : ReviewRepository {
    override suspend fun addReview(review: Review): ApiResult<Unit> {
        return runCatching {
            reviewsRef.child(review.staffId).child(review.id).setValue(review).await()
            ApiResult.Success(Unit)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun getStaffReviews(staffId: String): ApiResult<List<Review>> {
        return runCatching {
            val snapshot = reviewsRef.child(staffId).get().await()
            val reviews = snapshot.children.mapNotNull { it.getValue<Review>() }
            ApiResult.Success(reviews)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }
}