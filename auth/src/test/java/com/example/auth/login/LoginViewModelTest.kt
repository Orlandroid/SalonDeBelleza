package com.example.auth.login

import app.cash.turbine.test
import com.example.domain.UserPreferences
import com.example.domain.entities.remote.User
import com.example.domain.interfaces.EmailValidator
import com.example.domain.interfaces.PasswordValidator
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.use_cases.LoginUseCase
import com.google.common.truth.Truth.assertThat
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
class LoginViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val emailValidator: EmailValidator = mockk()
    private val passwordValidator: PasswordValidator = mockk()
    private val loginPreferences: UserPreferences = mockk(relaxed = true)
    private var loginUseCase: LoginUseCase = LoginUseCase(
        authRepository = authRepository,
        userPreferences = loginPreferences,
        userRepository = userRepository
    )


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(
            emailValidator = emailValidator,
            userPreferences = loginPreferences,
            passwordValidator = passwordValidator,
            loginUseCase = loginUseCase
        )
    }


    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun onUserNameChange_shouldUpdateUserNameAndValidateForm() = runTest(testDispatcher) {
        viewModel.state.test {
            //Given
            every { emailValidator.isValidEmail(any()) } returns true
            every { passwordValidator.isValidPassword(any()) } returns true

            val initialState = awaitItem()
            assert(initialState.userName.isEmpty())
            assertThat(initialState.showErrorUserName).isFalse()
            assertThat(initialState.showErrorPassword).isFalse()
            assertThat(initialState.isButtonLoginEnable).isFalse()
            val userName = "test@example.com"
            testDispatcher.scheduler.advanceUntilIdle()
            //When
            viewModel.onEvents(LoginEvents.OnUserNameChange(userName))

            val secondState = awaitItem()
            assertThat(secondState.userName).isEqualTo(userName)
            assertThat(secondState.isButtonLoginEnable).isTrue()
        }
    }

    @Test
    fun onUserNameChange_whenEmailValidPasswordInvalid_shouldShowPasswordError() =
        runTest(testDispatcher) {
            viewModel.state.test {
                //Given
                coEvery { emailValidator.isValidEmail(any()) } returns true
                coEvery { passwordValidator.isValidPassword(any()) } returns false

                val initialState = awaitItem()
                assert(initialState.userName.isEmpty())
                assertThat(initialState.showErrorUserName).isFalse()
                assertThat(initialState.showErrorPassword).isFalse()
                assertThat(initialState.isButtonLoginEnable).isFalse()
                val userName = "test@example.com"
                testDispatcher.scheduler.advanceUntilIdle()
                //When
                viewModel.onEvents(LoginEvents.OnUserNameChange(userName))
                val secondEmission = awaitItem()
                //Then
                assertThat(secondEmission.userName).isEqualTo(userName)
                assertThat(secondEmission.isButtonLoginEnable).isFalse()
                assertThat(secondEmission.showErrorPassword).isTrue()
                assertThat(secondEmission.showErrorUserName).isFalse()

            }
        }

    @Test
    fun onUserNameChange_whenBothEmailAndPasswordInvalid_shouldShowEmailError() = runTest {
        viewModel.state.test {
            //Given
            coEvery { emailValidator.isValidEmail(any()) } returns false
            coEvery { passwordValidator.isValidPassword(any()) } returns false

            val initialState = awaitItem()
            val userName = "invalid-email"
            assert(initialState.userName.isEmpty())
            assertThat(initialState.showErrorUserName).isFalse()
            assertThat(initialState.showErrorPassword).isFalse()
            assertThat(initialState.isButtonLoginEnable).isFalse()
            testDispatcher.scheduler.advanceUntilIdle()

            //When
            viewModel.onEvents(LoginEvents.OnUserNameChange(userName))
            val secondState = awaitItem()
            //Then
            assertThat(secondState.userName).isEqualTo(userName)
            assertThat(secondState.showErrorUserName).isTrue()
            assertThat(secondState.showErrorPassword).isTrue()
            assertThat(secondState.showErrorUserName).isTrue()
        }
    }

    @Test
    fun onLoginClick_whenLoginIsSuccess() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(
                email = any(),
                password = any()
            )
        } returns ApiResult.Success(
            Unit
        )
        val user: User = mockk(relaxed = true)
        coEvery { userRepository.getNameAndPhone() } returns ApiResult.Success(user)


        val initialState = viewModel.state.value
        assertThat(initialState.isLoading).isFalse()

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onEvents(LoginEvents.OnLoginClick)

        testDispatcher.scheduler.advanceUntilIdle()


        coVerify(exactly = 1) { loginPreferences.saveUserLogged() }
        coVerify(exactly = 1) { loginPreferences.saveUserEmail(any()) }

        viewModel.effects.test {
            val effect = awaitItem()
            assert(effect is LoginSideEffects.NavigateToHomeScreen)
        }
    }


    @Test
    fun onLoginClick_whenLoginFails() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(email = any(), password = any())
        } returns ApiResult.Error(
            error = "Invalid credentials"
        )

        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isFalse()
            assertThat(initialState.showDialogPasswordOrEmailWrong).isFalse()

            testDispatcher.scheduler.advanceUntilIdle()

            viewModel.onEvents(LoginEvents.OnLoginClick)

            coVerify(exactly = 0) { loginPreferences.saveUserLogged() }
            coVerify(exactly = 0) { loginPreferences.saveUserEmail(any()) }

            val finalState = awaitItem()
            assertThat(finalState.isLoading).isFalse()
            assertThat(finalState.showDialogPasswordOrEmailWrong).isTrue()
            assertThat(finalState.isLoading).isFalse()
        }
    }

}