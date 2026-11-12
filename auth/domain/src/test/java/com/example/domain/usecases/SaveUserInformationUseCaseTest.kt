package com.example.domain.usecases

import com.example.domain.entities.remote.User
import com.example.domain.repository.UserRepository
import com.example.model.state.ApiResult
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
    private val firebaseAuth: FirebaseAuth = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { firebaseAuth.uid } returns "user123"
        saveUserInformationUseCase = SaveUserInformationUseCase(
            userRepository = userRepository,
            firebaseAuth = firebaseAuth
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `when user has valid uid, saveUserInfo is called and returns Success`() = runTest {
        val user = User(name = "Ana", phone = "123", email = "ana@test.com", password = "pass", birthDay = "01/01", uid = "user123")
        coEvery { userRepository.saveUserInfo("user123", user) } returns ApiResult.Success(Any())

        val result = saveUserInformationUseCase.invoke(user)

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        coVerify(exactly = 1) { userRepository.saveUserInfo("user123", user) }
    }

    @Test
    fun `when uid is empty in user, fallback to firebaseAuth uid`() = runTest {
        val userWithoutUid = User(name = "Ana", phone = "123", email = "ana@test.com", password = "pass", birthDay = "01/01", uid = "")
        val expectedSavedUser = userWithoutUid.copy(uid = "user123")
        coEvery { userRepository.saveUserInfo("user123", expectedSavedUser) } returns ApiResult.Success(Any())

        val result = saveUserInformationUseCase.invoke(userWithoutUid)

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        coVerify(exactly = 1) { userRepository.saveUserInfo("user123", expectedSavedUser) }
    }

    @Test
    fun `when uid and firebaseAuth uid are both empty, returns Error`() = runTest {
        every { firebaseAuth.uid } returns null
        val userWithoutUid = User(name = "Ana", phone = "123", email = "ana@test.com", password = "pass", birthDay = "01/01", uid = "")

        val result = saveUserInformationUseCase.invoke(userWithoutUid)

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 0) { userRepository.saveUserInfo(any(), any()) }
    }
}
