package com.example.domain.use_cases


import com.example.domain.entities.Review
import com.example.domain.loyalty.LoyaltyTransactionType
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.repository.ReviewRepository
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getResultOrNull
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.loyalty.EarnPointsUseCase
import javax.inject.Inject

class SubmitAppointmentReviewUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val appointmentsRepository: AppointmentsRepository,
    private val reviewRepository: ReviewRepository,
    private val earnPointsUseCase: EarnPointsUseCase
) {
    suspend operator fun invoke(
        appointmentId: String,
        rating: Int,
        comment: String
    ): ApiResult<Unit> {

        val userResult = userRepository.getUser()
        val firebaseUser = userResult.getResultOrNull() ?: return ApiResult.Error("User not found")

        val userInfoResult = userRepository.getNameAndPhone()
        val userName =
            if (userInfoResult.isSuccess()) userInfoResult.getContent().name else "Customer"


        val appointmentResult = appointmentsRepository.getAppointmentById(appointmentId)
        if (appointmentResult.isError()) return ApiResult.Error("Appointment info not found")

        val fullAppointment = appointmentResult.getContent()

        val searchResult = appointmentsRepository.findStaff(
            branchName = fullAppointment.establishment,
            staffName = fullAppointment.employee
        )

        val (branchId, staff) = (searchResult as? ApiResult.Success)?.result ?: Pair("", null)
        val compositeStaffId =
            if (branchId.isNotEmpty() && staff != null) "${branchId}_${staff.id}" else "unknown"

        val review = Review(
            userId = firebaseUser.uid,
            userName = userName,
            staffId = compositeStaffId,
            staffName = fullAppointment.employee,
            appointmentId = appointmentId,
            rating = rating,
            comment = comment
        )

        val reviewResult = reviewRepository.addReview(review)
        if (reviewResult.isError()) return reviewResult

        return earnPointsUseCase(
            userId = firebaseUser.uid,
            pointsEarned = 50,
            sourceId = review.id,
            description = "Reward for leaving a review",
            type = LoyaltyTransactionType.REVIEW_EARNED
        )
    }
}