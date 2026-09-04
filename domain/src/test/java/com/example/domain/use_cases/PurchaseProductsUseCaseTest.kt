package com.example.domain.use_cases

import com.example.domain.state.ApiResult
import com.example.domain.state.getErrorMessage
import com.example.domain.transaction.TransactionRepository
import com.example.domain.transaction.TransactionType
import com.example.domain.wallet.Balance
import com.example.domain.wallet.Currency
import com.example.domain.wallet.WalletRepository
import io.mockk.coEvery
import io.mockk.coVerify
import com.google.common.truth.Truth.assertThat
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
class PurchaseProductsUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private var purchaseProductsUseCase: PurchaseProductsUseCase = mockk()
    private val walletRepository: WalletRepository = mockk()
    private val transactionRepository: TransactionRepository = mockk()
    private val getWalletUseCase: GetWalletUseCase = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        purchaseProductsUseCase = PurchaseProductsUseCase(
            walletRepository = walletRepository,
            transactionRepository = transactionRepository,
            getWalletUseCase = getWalletUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun `When getWallet Return Error `() = runTest {

        coEvery { getWalletUseCase.invoke() } returns ApiResult.Error()

        val purchaseResult = purchaseProductsUseCase.invoke(
            amount = 300L,
            transactionType = TransactionType.MARKETPLACE_PURCHASE,
            description = "Some description"
        )

        coVerify(exactly = 1) { getWalletUseCase.invoke() }
        coVerify(exactly = 0) { walletRepository.updateBalance(any()) }
        coVerify(inverse = true) { transactionRepository.createTransaction(any()) }
        assertThat(purchaseResult).isInstanceOf(ApiResult.Error::class.java)
        assertThat(purchaseResult.getErrorMessage()).isEqualTo("Unable to make the purchase")
    }

    @Test
    fun `When getWallet  Return Success but The user don,t have enough money `() =
        runTest {

            val balance = Balance(
                userId = "",
                userName = "",
                balance = 50L,
                currency = Currency.USD,
                createdAtMillis = System.currentTimeMillis()
            )
            coEvery { getWalletUseCase.invoke() } returns ApiResult.Success(balance)

            val purchaseResult = purchaseProductsUseCase.invoke(
                amount = 1000L,
                transactionType = TransactionType.MARKETPLACE_PURCHASE,
                description = "Some description"
            )

            coVerify(exactly = 1) { getWalletUseCase.invoke() }
            coVerify(exactly = 0) { walletRepository.updateBalance(any()) }
            coVerify(exactly = 0) { transactionRepository.createTransaction(any()) }
            assertThat(purchaseResult).isInstanceOf(ApiResult.Error::class.java)
            assertThat(purchaseResult.getErrorMessage()).isEqualTo("Insufficient balance")
        }


    @Test
    fun `When getWallet  Return Success but Update balance fails `() =
        runTest {

            val balance = Balance(
                userId = "",
                userName = "",
                balance = 5000L,
                currency = Currency.USD,
                createdAtMillis = System.currentTimeMillis()
            )
            coEvery { getWalletUseCase.invoke() } returns ApiResult.Success(balance)
            coEvery { walletRepository.updateBalance(any()) } returns ApiResult.Error()

            val purchaseResult = purchaseProductsUseCase.invoke(
                amount = 1000L,
                transactionType = TransactionType.MARKETPLACE_PURCHASE,
                description = "Some description"
            )

            coVerify(exactly = 1) { getWalletUseCase.invoke() }
            coVerify(exactly = 1) { walletRepository.updateBalance(any()) }
            coVerify(exactly = 0) { transactionRepository.createTransaction(any()) }
            assertThat(purchaseResult).isInstanceOf(ApiResult.Error::class.java)
            assertThat(purchaseResult.getErrorMessage()).isEqualTo("Unable to update balance")
        }


    @Test
    fun `When getWallet  Return Success but createTransaction fails `() =
        runTest {
            val balance = Balance(
                userId = "",
                userName = "",
                balance = 5000L,
                currency = Currency.USD,
                createdAtMillis = System.currentTimeMillis()
            )
            coEvery { getWalletUseCase.invoke() } returns ApiResult.Success(balance)
            coEvery { walletRepository.updateBalance(any()) } returns ApiResult.Success(Unit)
            coEvery { transactionRepository.createTransaction(any()) } returns ApiResult.Error()

            val purchaseResult = purchaseProductsUseCase.invoke(
                amount = 1000L,
                transactionType = TransactionType.MARKETPLACE_PURCHASE,
                description = "Some description"
            )

            coVerify(exactly = 1) { getWalletUseCase.invoke() }
            coVerify(exactly = 1) { walletRepository.updateBalance(any()) }
            coVerify(exactly = 1) { transactionRepository.createTransaction(any()) }
            assertThat(purchaseResult).isInstanceOf(ApiResult.Error::class.java)
            assertThat(purchaseResult.getErrorMessage()).isEqualTo("Creation of transaction fail")
        }

    @Test
    fun `When all the services success `() =
        runTest {
            val balance = Balance(
                userId = "",
                userName = "",
                balance = 5000L,
                currency = Currency.USD,
                createdAtMillis = System.currentTimeMillis()
            )
            coEvery { getWalletUseCase.invoke() } returns ApiResult.Success(balance)
            coEvery { walletRepository.updateBalance(any()) } returns ApiResult.Success(Unit)
            coEvery { transactionRepository.createTransaction(any()) } returns ApiResult.Success(Unit)

            val purchaseResult = purchaseProductsUseCase.invoke(
                amount = 1000L,
                transactionType = TransactionType.MARKETPLACE_PURCHASE,
                description = "Some description"
            )

            coVerify(exactly = 1) { getWalletUseCase.invoke() }
            coVerify(exactly = 1) { walletRepository.updateBalance(any()) }
            coVerify(exactly = 1) { transactionRepository.createTransaction(any()) }
            assertThat(purchaseResult).isInstanceOf(ApiResult.Success::class.java)
        }

}