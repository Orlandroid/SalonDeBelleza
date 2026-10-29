package com.example.domain.usecases

import com.example.domain.entities.Review
import com.example.domain.entities.StaffRatingSummary
import com.example.domain.repository.ReviewRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetStaffRatingUseCaseTest {
    private lateinit var getStaffRatingUseCaseTest: GetStaffRatingUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val reviewRepository: ReviewRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getStaffRatingUseCaseTest = GetStaffRatingUseCase(reviewRepository = reviewRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `when getStaffReviews returns Error`() =
        runTest {
            coEvery { reviewRepository.getStaffReviews(any()) } returns ApiResult.Error()

            val response = getStaffRatingUseCaseTest.invoke("78")

            coVerify(exactly = 1) { reviewRepository.getStaffReviews(any()) }
            Truth.assertThat(response).isInstanceOf(ApiResult.Error::class.java)
        }

    @Test
    fun `when getStaffReviews returns Success but don,t have any review`() =
        runTest {
            coEvery { reviewRepository.getStaffReviews(any()) } returns ApiResult.Success(emptyList())

            val response = getStaffRatingUseCaseTest.invoke("78")

            coVerify(exactly = 1) { reviewRepository.getStaffReviews(any()) }
            Truth.assertThat(response.getContent()).isEqualTo(StaffRatingSummary())
            Truth.assertThat(response).isInstanceOf(ApiResult.Success::class.java)
        }

    @Test
    fun `when getStaffReviews returns Success with list of reviews`() =
        runTest {
            val sampleReviews =
                listOf(
                    Review(id = "1", staffId = "78", rating = 4, comment = "Great service"),
                    Review(id = "2", staffId = "78", rating = 5, comment = "Excellent!")
                )
            coEvery { reviewRepository.getStaffReviews("78") } returns ApiResult.Success(sampleReviews)

            val response = getStaffRatingUseCaseTest.invoke("78")

            coVerify(exactly = 1) { reviewRepository.getStaffReviews("78") }
            Truth.assertThat(response).isInstanceOf(ApiResult.Success::class.java)

            val summary = response.getContent()
            Truth.assertThat(summary.totalReviews).isEqualTo(2)
            Truth.assertThat(summary.averageRating).isEqualTo(4.5)
            Truth.assertThat(summary.recentComments).isEqualTo(sampleReviews)
        }
}
