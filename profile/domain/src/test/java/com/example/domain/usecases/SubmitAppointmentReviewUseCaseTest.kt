package com.example.domain.usecases

import com.example.domain.Appointment
import com.example.domain.entities.Review
import com.example.domain.entities.remote.User
import com.example.domain.entities.remote.migration.Staff
import com.example.domain.loyalty.LoyaltyTransactionType
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.repository.ReviewRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.loyalty.EarnPointsUseCase
import com.example.model.state.ApiResult
import com.example.model.state.getErrorMessage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SubmitAppointmentReviewUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private val userRepository: UserRepository = mockk()
    private val appointmentsRepository: AppointmentsRepository = mockk()
    private val reviewRepository: ReviewRepository = mockk()
    private val earnPointsUseCase: EarnPointsUseCase = mockk()

    private lateinit var useCase: SubmitAppointmentReviewUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = SubmitAppointmentReviewUseCase(
            userRepository = userRepository,
            appointmentsRepository = appointmentsRepository,
            reviewRepository = reviewRepository,
            earnPointsUseCase = earnPointsUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `when submitting review succeeds, adds review and earns loyalty points`() = runTest {
        val user = User(uid = "user123", name = "Ana", phone = "123", email = "a@a.com", password = "p", birthDay = "01/01")
        val appointment = Appointment(
            id = "app123",
            idAppointment = "app123",
            branch = "Centro",
            employee = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            clientName = "Ana"
        )
        val staff = Staff(id = "emp1", image_url = "", name = "Carlos", gender = "M", rating = 5)

        coEvery { userRepository.getUser() } returns ApiResult.Success(user)
        coEvery { appointmentsRepository.getAppointmentById("app123") } returns ApiResult.Success(appointment)
        coEvery { appointmentsRepository.findStaff("Centro", "Carlos") } returns ApiResult.Success(Pair("branch123", staff))

        val reviewSlot = slot<Review>()
        coEvery { reviewRepository.addReview(capture(reviewSlot)) } returns ApiResult.Success(Unit)
        coEvery {
            earnPointsUseCase(
                userId = "user123",
                pointsEarned = 50,
                sourceId = any(),
                description = any(),
                type = LoyaltyTransactionType.REVIEW_EARNED
            )
        } returns ApiResult.Success(Unit)

        val result = useCase(appointmentId = "app123", rating = 5, comment = "Great service!")

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        coVerify(exactly = 1) { reviewRepository.addReview(any()) }
        coVerify(exactly = 1) { earnPointsUseCase("user123", 50, any(), any(), LoyaltyTransactionType.REVIEW_EARNED) }
        assertThat(reviewSlot.captured.staffId).isEqualTo("branch123_emp1")
    }

    @Test
    fun `when user is not found, returns error`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error("User not logged in")

        val result = useCase(appointmentId = "app123", rating = 5, comment = "Great service!")

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.getErrorMessage()).isEqualTo("User not found")
        coVerify(exactly = 0) { reviewRepository.addReview(any()) }
    }
}
