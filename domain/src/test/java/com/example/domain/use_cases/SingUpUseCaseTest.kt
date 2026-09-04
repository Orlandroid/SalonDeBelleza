package com.example.domain.use_cases

import com.example.domain.repository.AuthRepository
import com.example.domain.state.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import io.mockk.coVerify
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SingUpUseCaseTest {

    private lateinit var singUpUseCase: SingUpUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val authRepository = mockk<AuthRepository>()
    private val createWalletUseCase = mockk<CreateWalletUseCase>()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        singUpUseCase = SingUpUseCase(
            authRepository = authRepository,
            createWalletUseCase = createWalletUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `When authResult Register Return Error `(): Unit =
        runTest {
            coEvery { authRepository.register(any(), any()) } returns ApiResult.Error()

            val signUpResult = singUpUseCase.invoke("", "")


            assertThat(signUpResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { authRepository.register(any(), any()) }
            coVerify(inverse = true) { createWalletUseCase.invoke(any()) }

        }


    @Test
    fun `When authResult Register Return Success and createWallet Return Error `(): Unit =
        runTest {

            val authResult = mockk<AuthResult>(relaxed = true)

            coEvery { authRepository.register(any(), any()) } returns ApiResult.Success(authResult)
            coEvery { createWalletUseCase.invoke(any()) } returns ApiResult.Error()

            val signUpResult = singUpUseCase.invoke("", "")


            coVerify(exactly = 1) { authRepository.register(any(), any()) }
            coVerify(exactly = 1) { createWalletUseCase.invoke(any()) }
            assertThat(signUpResult).isInstanceOf(ApiResult.Error::class.java)

        }

    @Test
    fun `When authResult Register Return Success and createWallet Return Success `(): Unit =
        runTest {

            val authResult = mockk<AuthResult>(relaxed = true)

            coEvery { authRepository.register(any(), any()) } returns ApiResult.Success(authResult)
            coEvery { createWalletUseCase.invoke(any()) } returns ApiResult.Success(Unit)

            val signUpResult = singUpUseCase.invoke("", "")


            coVerify(exactly = 1) { authRepository.register(any(), any()) }
            coVerify(exactly = 1) { createWalletUseCase.invoke(any()) }
            assertThat(signUpResult).isInstanceOf(ApiResult.Success::class.java)

        }


}