package com.example.domain.usecases

import com.example.domain.Product
import com.example.domain.loyalty.PromotionCode
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.loyalty.EarnPointsUseCase
import com.example.model.state.ApiResult
import com.example.model.state.getErrorMessage
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
class CheckoutCartUseCaseTest {
    private lateinit var checkoutCartUseCase: CheckoutCartUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val userRepository: UserRepository = mockk()
    private val purchaseProductsUseCase: PurchaseProductsUseCase = mockk()
    private val loyaltyRepository: LoyaltyRepository = mockk(relaxed = true)
    private val earnPointsUseCase: EarnPointsUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        checkoutCartUseCase =
            CheckoutCartUseCase(
                userRepository = userRepository,
                purchaseProductsUseCase = purchaseProductsUseCase,
                loyaltyRepository = loyaltyRepository,
                earnPointsUseCase = earnPointsUseCase
            )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `When user is not authenticated `() =
        runTest {
            coEvery { userRepository.getUser() } returns ApiResult.Error()

            val checkoutCartResult =
                checkoutCartUseCase.invoke(products = emptyList(), appliedPromo = null)

            Truth.assertThat(checkoutCartResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 0) { loyaltyRepository.usePromotionCode(any(), any()) }
            coVerify(exactly = 0) {
                purchaseProductsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }
            coVerify(exactly = 0) {
                earnPointsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }

            Truth
                .assertThat(checkoutCartResult.getErrorMessage())
                .isEqualTo("User not authenticated")
        }

    @Test
    fun `When user is authenticated but products list is empty`() =
        runTest {
            coEvery { userRepository.getUser() } returns ApiResult.Success(mockk(relaxed = true))

            val checkoutCartResult =
                checkoutCartUseCase.invoke(products = emptyList(), appliedPromo = null)

            Truth.assertThat(checkoutCartResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 0) { loyaltyRepository.usePromotionCode(any(), any()) }
            coVerify(exactly = 0) {
                purchaseProductsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }
            coVerify(exactly = 0) {
                earnPointsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }

            Truth
                .assertThat(checkoutCartResult.getErrorMessage())
                .isEqualTo("Cart is empty")
        }

    @Test
    fun `When user is authenticated and we have products`() =
        runTest {
            val product: Product = mockk<Product>(relaxed = true).copy(price = 5)

            val products = listOf(product, product, product, product)

            coEvery { userRepository.getUser() } returns ApiResult.Success(mockk(relaxed = true))

            coEvery {
                purchaseProductsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            } returns ApiResult.Error()

            val checkoutCartResult =
                checkoutCartUseCase.invoke(products = products, appliedPromo = null)

            Truth.assertThat(checkoutCartResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 0) { loyaltyRepository.usePromotionCode(any(), any()) }
            coVerify(exactly = 1) {
                purchaseProductsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }
            coVerify(exactly = 0) {
                earnPointsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }

            Truth
                .assertThat(checkoutCartResult.getErrorMessage())
                .isEqualTo("An error occurred")
        }

    @Test
    fun `When user is authenticated, we have products and purchaseProducts success`() =
        runTest {
            val product: Product = mockk<Product>(relaxed = true).copy(price = 5)

            val promotionCode: PromotionCode = mockk(relaxed = true)

            val products = listOf(product, product, product, product)

            coEvery { userRepository.getUser() } returns ApiResult.Success(mockk(relaxed = true))

            coEvery {
                purchaseProductsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            } returns ApiResult.Success(Unit)

            coEvery {
                earnPointsUseCase.invoke(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns ApiResult.Success(Unit)

            val checkoutCartResult =
                checkoutCartUseCase.invoke(products = products, appliedPromo = promotionCode)

            Truth.assertThat(checkoutCartResult).isInstanceOf(ApiResult.Success::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 1) { loyaltyRepository.usePromotionCode(any(), any()) }
            coVerify(exactly = 1) {
                purchaseProductsUseCase.invoke(
                    any(),
                    any(),
                    any()
                )
            }
            coVerify(exactly = 1) {
                earnPointsUseCase.invoke(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        }
}
