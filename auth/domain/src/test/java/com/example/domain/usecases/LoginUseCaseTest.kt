package com.example.domain.usecases

import com.example.domain.UserPreferences
import com.example.domain.entities.remote.User
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.models.UserRole
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
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val authRepository: AuthRepository = mockk()
    private val userPreferences: UserPreferences = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginUseCase = LoginUseCase(
            authRepository = authRepository,
            userPreferences = userPreferences,
            userRepository = userRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `When login Throws an Error `(): Unit =
        runTest {

            coEvery { authRepository.login(any(), any()) } returns ApiResult.Error()


            val loginResult = loginUseCase.invoke("", "")

            Truth.assertThat(loginResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { authRepository.login(any(), any()) }
            coVerify(exactly = 0) { userRepository.getUser() }
            coVerify(exactly = 0) { userPreferences.saveUserLogged() }
            coVerify(exactly = 0) { userPreferences.saveUserEmail(any()) }

        }

    @Test
    fun `When login Success, getNameAndPhone is Success `(): Unit =
        runTest {
            val user = User(
                name = "",
                phone = "",
                email = "",
                password = "",
                birthDay = "",
                role = UserRole.ADMIN,
                uid = ""
            )
            coEvery { authRepository.login(any(), any()) } returns ApiResult.Success(Unit)
            coEvery { userRepository.getUser() } returns ApiResult.Success(user)


            val loginResult = loginUseCase.invoke("", "")

            Truth.assertThat(loginResult).isInstanceOf(ApiResult.Success::class.java)
            coVerify(exactly = 1) { authRepository.login(any(), any()) }
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 1) { userPreferences.saveUserLogged() }
            coVerify(exactly = 1) { userPreferences.saveUserEmail(any()) }
            Truth.assertThat(loginResult.getContent()).isEqualTo(UserRole.ADMIN)
        }

    @Test
    fun `When login Success, getNameAndPhone Throws an Error`(): Unit =
        runTest {
            coEvery { authRepository.login(any(), any()) } returns ApiResult.Success(Unit)
            coEvery { userRepository.getUser() } returns ApiResult.Error()


            val loginResult = loginUseCase.invoke("", "")

            Truth.assertThat(loginResult).isInstanceOf(ApiResult.Success::class.java)
            coVerify(exactly = 1) { authRepository.login(any(), any()) }
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 1) { userPreferences.saveUserLogged() }
            coVerify(exactly = 1) { userPreferences.saveUserEmail(any()) }
            Truth.assertThat(loginResult.getContent()).isEqualTo(UserRole.CUSTOMER)
        }


}