package com.example.domain.use_cases

import com.example.domain.entities.remote.User
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseUser
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
class SaveUserInformationUseCaseTest {


    private lateinit var saveUserInformationUseCase: SaveUserInformationUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val userRepository: UserRepository = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        saveUserInformationUseCase = SaveUserInformationUseCase(
            userRepository = userRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `when getUser Return Error`(): Unit =
        runTest {
            val user: User = mockk(relaxed = true)
            coEvery { userRepository.getUser() } returns ApiResult.Error()

            val loginResult = saveUserInformationUseCase.invoke(user)

            Truth.assertThat(loginResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 0) { userRepository.saveUserInfo(any(), any()) }

        }


    @Test
    fun `when getUser Return Success , and saveUserInfo Return Success `(): Unit =
        runTest {
            val user = User(name = "", phone = "", email = "", password = "", birthDay = "", uid = "sysh")
            coEvery { userRepository.getUser() } returns ApiResult.Success(user)
            coEvery { userRepository.saveUserInfo(any(), any()) } returns ApiResult.Success(Any())

            val loginResult = saveUserInformationUseCase.invoke(user)

            Truth.assertThat(loginResult).isInstanceOf(ApiResult.Success::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 1) { userRepository.saveUserInfo(any(), any()) }

        }


    @Test
    fun `when getUser Return Success , but getContent uid is null `(): Unit =
        runTest {
            val user = User(name = "", phone = "", email = "", password = "", birthDay = "", uid = "")
            coEvery { userRepository.getUser() } returns ApiResult.Success(user)

            val loginResult = saveUserInformationUseCase.invoke(user)

            Truth.assertThat(loginResult).isInstanceOf(ApiResult.Error::class.java)
            coVerify(exactly = 1) { userRepository.getUser() }
            coVerify(exactly = 0) { userRepository.saveUserInfo(any(), any()) }

        }

}