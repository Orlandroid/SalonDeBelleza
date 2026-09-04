package com.example.domain.use_cases

import com.example.domain.entities.UserProfile
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
    private val testDispatcher = StandardTestDispatcher()
    private val getUserInfoUseCase: GetUserInfoUseCase = mockk()
    private val getWalletUseCase: GetWalletUseCase = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getBalanceUseCase = GetBalanceUseCase(
            getUserInfoUseCase = getUserInfoUseCase,
            getWalletUseCase = getWalletUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun `when getUserInfoUseCase return  Error `() = runTest {

        coEvery { getUserInfoUseCase.invoke() } returns ApiResult.Error()

        val getBalanceResult = getBalanceUseCase.invoke()


        Truth.assertThat(getBalanceResult).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 0) { getWalletUseCase.invoke() }
    }

    @Test
    fun `when getUserInfoUseCase return  Success and getWalletUseCase return Error `() = runTest {

        val userProfile: UserProfile = mockk(relaxed = true)

        coEvery { getUserInfoUseCase.invoke() } returns ApiResult.Success(userProfile)
        coEvery { getWalletUseCase.invoke() } returns ApiResult.Error()

        val getBalanceResult = getBalanceUseCase.invoke()


        Truth.assertThat(getBalanceResult).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 1) { getWalletUseCase.invoke() }
    }

    @Test
    fun `when getUserInfoUseCase return  Success and getWalletUseCase return Success `() = runTest {

        val userProfile: UserProfile = mockk(relaxed = true)

        coEvery { getUserInfoUseCase.invoke() } returns ApiResult.Success(userProfile)
        coEvery { getWalletUseCase.invoke() } returns ApiResult.Success(mockk(relaxed = true))

        val getBalanceResult = getBalanceUseCase.invoke()


        Truth.assertThat(getBalanceResult).isInstanceOf(ApiResult.Success::class.java)
        coVerify(exactly = 1) { getWalletUseCase.invoke() }
    }

}