package com.example.domain.usecases


import com.example.domain.UserSessionStatus
import com.example.domain.entities.remote.User
import com.example.domain.loyalty.Loyalty
import com.example.domain.loyalty.PromotionCode
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.repository.UserRepository
import com.example.domain.repository.WalletRepository
import com.example.domain.wallet.Wallet
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetUserInfoUseCaseTest {


    private val userRepository: UserRepository = mockk(relaxed = true)
    private val walletRepository: WalletRepository = mockk(relaxed = true)
    private val loyaltyRepository: LoyaltyRepository = mockk(relaxed = true)
    private lateinit var useCase: GetUserInfoUseCase
    private val mockUser = User(
        name = "John Doe",
        phone = "555-1234",
        email = "test@example.com",
        password = "",
        birthDay = "",
        uid = "uid-123"
    )

    @Before
    fun setUp() {
        useCase =
            GetUserInfoUseCase(
                walletRepository = walletRepository,
                userRepository = userRepository,
                loyaltyRepository = loyaltyRepository
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
            coEvery { userRepository.getUser() } returns ApiResult.Success(mockk(relaxed = true))
            coEvery { userRepository.getUserImage() } returns ApiResult.Success("https://image.url/pic.png")
            coEvery { walletRepository.getWallet() } returns ApiResult.Success(Wallet())
            coEvery { loyaltyRepository.getLoyalty(any()) } returns ApiResult.Success(Loyalty())
            coEvery { loyaltyRepository.getPromotionCodes(any()) } returns ApiResult.Success(
                listOf(
                    PromotionCode()
                )
            )
            coEvery { userRepository.getUser() } returns
                    ApiResult.Success(mockUser)

            val result = useCase.invoke()

            assertThat(result).isInstanceOf(ApiResult.Success::class.java)
            val profile = (result as ApiResult.Success).result

            assertThat(profile.name).isEqualTo("John Doe")
            assertThat(profile.email).isEqualTo("test@example.com")
            assertThat(profile.uid).isEqualTo("uid-123")
            assertThat(profile.phone).isEqualTo("555-1234")
            assertThat(profile.image).isEqualTo("https://image.url/pic.png")
            assertThat(profile.sessionStatus).isEqualTo(UserSessionStatus.ACTIVE)
        }

    @Test
    fun `invoke returns Error when authRepository getUser fails`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error("Auth failed")

        val result = useCase.invoke()


        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result).isEqualTo(ApiResult.Error<FirebaseUser>("Auth failed"))

        coVerify(exactly = 0) { userRepository.getUserImage() }
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `invoke returns Error with default message when error has no explicit message`() = runTest {

        coEvery { userRepository.getUser() } returns ApiResult.Error("User not found")

        val result = useCase.invoke()


        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result).isEqualTo(ApiResult.Error<FirebaseUser>("User not found"))

        coVerify(exactly = 0) { userRepository.getUserImage() }
        coVerify(exactly = 1) { userRepository.getUser() }
    }


    @Test
    fun `invoke defaults money to zero when getUserMoney fails`() = runTest {
        val wallet: Wallet = mockk(relaxed = true)
        coEvery { walletRepository.getWallet() } returns ApiResult.Success(wallet)
        coEvery { loyaltyRepository.getLoyalty(any()) } returns ApiResult.Success(Loyalty())
        coEvery { loyaltyRepository.getPromotionCodes(any()) } returns ApiResult.Success(
            listOf(
                PromotionCode()
            )
        )
        coEvery { userRepository.getUserImage() } returns ApiResult.Success("image.png")
        coEvery { userRepository.getUser() } returns
                ApiResult.Success(mockUser)

        val result = useCase.invoke()

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        assertThat(result.getContent().money).isEqualTo(0L)
    }

    @Test
    fun `invoke sets image to null when getUserImage fails`() = runTest {
        val user = mockFirebaseUser(email = "test@example.com")
        val wallet: Wallet = mockk(relaxed = true)
        coEvery { walletRepository.getWallet() } returns ApiResult.Success(wallet)
        coEvery { loyaltyRepository.getLoyalty(any()) } returns ApiResult.Success(Loyalty())
        coEvery { loyaltyRepository.getPromotionCodes(any()) } returns ApiResult.Success(
            listOf(
                PromotionCode()
            )
        )
        coEvery { userRepository.getUserImage() } returns ApiResult.Error("Image not found")
        coEvery { userRepository.getUser() } returns
                ApiResult.Success(mockUser)

        val result = useCase.invoke()

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val profile = (result as ApiResult.Success).result
        assertThat(profile.image).isNull()
    }

    @Test
    fun `invoke sets session status ACTIVE when user is successfully fetched`() = runTest {
        val wallet: Wallet = mockk(relaxed = true)
        coEvery { walletRepository.getWallet() } returns ApiResult.Success(wallet)
        coEvery { loyaltyRepository.getLoyalty(any()) } returns ApiResult.Success(Loyalty())
        coEvery { loyaltyRepository.getPromotionCodes(any()) } returns ApiResult.Success(
            listOf(
                PromotionCode()
            )
        )
        val user = mockFirebaseUser(uid = "uid-123", email = "test@example.com")
        coEvery { userRepository.getUserImage() } returns ApiResult.Success("img")
        coEvery { userRepository.getUser() } returns
                ApiResult.Success(mockUser)

        val result = useCase.invoke() as ApiResult.Success

        assertThat(result.result.sessionStatus).isEqualTo(UserSessionStatus.ACTIVE)
        coVerify(exactly = 2) { userRepository.getUser() }
    }

    @Test
    fun `invoke sets session status INACTIVE when getUser fails`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error("Auth failed")

        val result = useCase.invoke()
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 1) { userRepository.getUser() }
    }

}