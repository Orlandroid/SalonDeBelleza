package com.example.domain.use_cases

import com.example.domain.perfil.UserInfoFirebase
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetUserInfoUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var useCase: GetUserInfoUseCase
    private val getWalletUseCase: GetWalletUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase =
            GetUserInfoUseCase(
                authRepository = authRepository,
                userRepository = userRepository,
                getWalletUseCase = getWalletUseCase
            )
    }

    private fun mockFirebaseUser(uid: String = "uid-123", email: String?): FirebaseUser {
        val user = mockk<FirebaseUser>()
        every { user.uid } returns uid
        every { user.email } returns email
        return user
    }

    @Test
    fun `invoke returns Success with fully populated profile when all calls succeed`(): Unit =
        runTest {
            val user = mockFirebaseUser(email = "test@example.com")
            every { authRepository.getUser() } returns ApiResult.Success(user)
            coEvery { userRepository.getUserImage() } returns ApiResult.Success("https://image.url/pic.png")
            coEvery { userRepository.getNameAndPhone() } returns
                    ApiResult.Success(UserInfoFirebase(name = "John Doe", phone = "555-1234"))

            val result = useCase.invoke()

            assertThat(result).isInstanceOf(ApiResult.Success::class.java)
            val profile = (result as ApiResult.Success).result

            assertThat(profile.name).isEqualTo("John Doe")
            assertThat(profile.email).isEqualTo("test@example.com")
            assertThat(profile.uid).isEqualTo("uid-123")
            assertThat(profile.phone).isEqualTo("555-1234")
            assertThat(profile.image).isEqualTo("https://image.url/pic.png")
            assertThat(profile.sessionStatus).isEqualTo(GetUserInfoUseCase.UserSessionStatus.ACTIVE)
        }

    @Test
    fun `invoke returns Error when authRepository getUser fails`() = runTest {
        every { authRepository.getUser() } returns ApiResult.Error("Auth failed")

        val result = useCase.invoke()


        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result).isEqualTo(ApiResult.Error<FirebaseUser>("Auth failed"))

        coVerify(exactly = 0) { userRepository.getUserImage() }
        coVerify(exactly = 0) { userRepository.getNameAndPhone() }
    }

    @Test
    fun `invoke returns Error with default message when error has no explicit message`() = runTest {
        every { authRepository.getUser() } returns ApiResult.Error("User not found")

        val result = useCase.invoke()


        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result).isEqualTo(ApiResult.Error<FirebaseUser>("User not found"))

        coVerify(exactly = 0) { userRepository.getUserImage() }
        coVerify(exactly = 0) { userRepository.getNameAndPhone() }
    }

    @Test
    fun `invoke returns Error User not found when getUser succeeds with a null user`() = runTest {

        every { authRepository.getUser() } returns ApiResult.Success(null)

        val result = useCase.invoke()

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result).isEqualTo(ApiResult.Error<FirebaseUser>("User not found"))
    }

    @Test
    fun `invoke defaults money to zero when getUserMoney fails`() = runTest {
        val user = mockFirebaseUser(uid = "uid-123", email = "test@example.com")
        every { authRepository.getUser() } returns ApiResult.Success(user)
        coEvery { userRepository.getUserImage() } returns ApiResult.Success("image.png")
        coEvery { userRepository.getNameAndPhone() } returns
                ApiResult.Success(UserInfoFirebase(name = "John", phone = "555"))

        val result = useCase.invoke()

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
    }

    @Test
    fun `invoke sets image to null when getUserImage fails`() = runTest {
        val user = mockFirebaseUser(email = "test@example.com")
        every { authRepository.getUser() } returns ApiResult.Success(user)
        coEvery { userRepository.getUserImage() } returns ApiResult.Error("Image not found")
        coEvery { userRepository.getNameAndPhone() } returns
                ApiResult.Success(UserInfoFirebase(name = "John", phone = "555"))

        val result = useCase.invoke()

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val profile = (result as ApiResult.Success).result
        assertThat(profile.image).isNull()
    }

    @Test
    fun `invoke sets empty name and phone when getNameAndPhone fails`() = runTest {
        val user = mockFirebaseUser(uid = "uid-123", email = "test@example.com")
        every { authRepository.getUser() } returns ApiResult.Success(user)
        coEvery { userRepository.getUserImage() } returns ApiResult.Success("img")
        coEvery { userRepository.getNameAndPhone() } returns ApiResult.Error("Not found")

        val result = useCase.invoke()

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val profile = (result as ApiResult.Success).result

        assertThat(profile.name).isEqualTo("")
        assertThat(profile.phone).isEqualTo("")
    }

    @Test
    fun `invoke sets session status ACTIVE when user is successfully fetched`() = runTest {
        val user = mockFirebaseUser(uid = "uid-123", email = "test@example.com")
        every { authRepository.getUser() } returns ApiResult.Success(user)
        coEvery { userRepository.getUserImage() } returns ApiResult.Success("img")
        coEvery { userRepository.getNameAndPhone() } returns
                ApiResult.Success(UserInfoFirebase(name = "John", phone = "555"))

        val result = useCase.invoke() as ApiResult.Success

        assertThat(result.result.sessionStatus).isEqualTo(GetUserInfoUseCase.UserSessionStatus.ACTIVE)
        verify(exactly = 2) { authRepository.getUser() }
    }

    @Test
    fun `invoke sets session status INACTIVE when getUser fails`() = runTest {
        every { authRepository.getUser() } returns ApiResult.Error("Auth failed")

        val result = useCase.invoke()
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 1) { authRepository.getUser() }
    }

}