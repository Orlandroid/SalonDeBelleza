package com.example.domain.use_cases.loyalty

import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
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
class VerifyPromoCodeUseCaseTest {

    private lateinit var verifyPromoCodeUseCase: VerifyPromoCodeUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val repository: LoyaltyRepository = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        verifyPromoCodeUseCase = VerifyPromoCodeUseCase(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun `when getPromotionCode return ApiResult Error`() = runTest {

        coEvery { repository.getPromotionCodes(any()) } returns ApiResult.Error("error")

        val response = verifyPromoCodeUseCase.invoke("code", codeStr = "fakecode")


        coVerify(exactly = 1) { repository.getPromotionCodes(any()) }
        Truth.assertThat(response).isInstanceOf(ApiResult.Error::class.java)
    }
}

