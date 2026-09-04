package com.example.domain.use_cases

import com.example.domain.state.ApiResult
import com.example.domain.wallet.Wallet
import com.example.domain.wallet.WalletRepository
import com.google.common.truth.Truth.assertThat
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
class CreateWalletUseCaseTest {


    private lateinit var createWalletUseCase: CreateWalletUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val walletRepository = mockk<WalletRepository>(relaxed = true)


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        createWalletUseCase = CreateWalletUseCase(walletRepository = walletRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    /**
     * it means that the user already has one wallet created there is no reason for create another
     */
    @Test
    fun `when getWalletReturn Success immediately`() = runTest {
        val wallet = Wallet()
        coEvery { walletRepository.getWallet() } returns ApiResult.Success(wallet)

        val response = createWalletUseCase.invoke("fakeUserId")

        coVerify(inverse = true) { walletRepository.createWallet(wallet) }
        assertThat(response).isInstanceOf(ApiResult.Success::class.java)
    }


    @Test
    fun `when getWalletReturn Error and createWallet return Error`() = runTest {
        coEvery { walletRepository.getWallet() } returns ApiResult.Error()
        coEvery { walletRepository.createWallet(any()) } returns ApiResult.Error()

        val response = createWalletUseCase.invoke("fakeUserId")

        coVerify(exactly = 1) { walletRepository.getWallet() }
        coVerify(exactly = 1) { walletRepository.createWallet(any()) }
        assertThat(response).isInstanceOf(ApiResult.Error::class.java)
    }

    @Test
    fun `when getWalletReturn Error and createWallet return Success`() = runTest {
        coEvery { walletRepository.getWallet() } returns ApiResult.Error()
        coEvery { walletRepository.createWallet(any()) } returns ApiResult.Success(Unit)

        val response = createWalletUseCase.invoke("fakeUserId")

        coVerify(exactly = 1) { walletRepository.getWallet() }
        coVerify(exactly = 1) { walletRepository.createWallet(any()) }
        assertThat(response).isInstanceOf(ApiResult.Success::class.java)
    }


}