package com.example.domain.use_cases

import com.example.domain.entities.remote.User
import com.example.domain.repository.UserRepository
import com.example.domain.repository.WalletRepository
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
class GetBalanceUseCaseTest {


    private lateinit var getBalanceUseCase: GetBalanceUseCase
    private val userRepository: UserRepository = mockk()
    private val walletRepository: WalletRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getBalanceUseCase = GetBalanceUseCase(
            userRepository = userRepository,
            walletRepository = walletRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun `when getUserInfoUseCase return  Error `() = runTest {

        coEvery { userRepository.getNameAndPhone() } returns ApiResult.Error()

        val getBalanceResult = getBalanceUseCase.invoke()


        Truth.assertThat(getBalanceResult).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 0) { walletRepository.getWallet() }
    }

    @Test
    fun `when getUserInfoUseCase return  Success and getWalletUseCase return Error `() = runTest {

        val userProfile: User = mockk(relaxed = true)

        coEvery { userRepository.getNameAndPhone() } returns ApiResult.Success(userProfile)
        coEvery { walletRepository.getWallet() } returns ApiResult.Error()

        val getBalanceResult = getBalanceUseCase.invoke()


        Truth.assertThat(getBalanceResult).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 1) { walletRepository.getWallet() }
    }

    @Test
    fun `when getUserInfoUseCase return  Success and getWalletUseCase return Success `() = runTest {

        val userProfile: User = mockk(relaxed = true)

        coEvery { userRepository.getNameAndPhone() } returns ApiResult.Success(userProfile)
        coEvery { walletRepository.getWallet() } returns ApiResult.Success(mockk(relaxed = true))

        val getBalanceResult = getBalanceUseCase.invoke()


        Truth.assertThat(getBalanceResult).isInstanceOf(ApiResult.Success::class.java)
        coVerify(exactly = 1) { walletRepository.getWallet() }
    }

}