package com.example.citassalon.presentacion.features.auth.login

import app.cash.turbine.test
import com.example.domain.validation.EmailValidator
import com.example.domain.validation.PasswordValidator
import com.example.data.preferences.LoginPreferences
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private val authRepository: AuthRepository = mockk()
    private val emailValidator: EmailValidator = mockk()
    private val passwordValidator: PasswordValidator = mockk()
    private val loginPreferences: LoginPreferences = mockk(relaxed = true)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(
            authRepository = authRepository,
            emailValidator = emailValidator,
            ioDispatcher = testDispatcher,
            loginPreferences = loginPreferences,
            passwordValidator = passwordValidator
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun initialState_shouldHaveCorrectDefaultValues() = runTest {

    }

    @Test
    fun onUserNameChange_shouldUpdateUserNameAndValidateForm() = runTest {
        viewModel.state.test {
            //Given
            coEvery { emailValidator.isValidEmail(any()) } returns true
            coEvery { passwordValidator.isValidPassword(any()) } returns true

            val initialState = awaitItem()
            assert(initialState.userName.isEmpty())
            assertThat(initialState.showErrorUserName).isFalse()
            assertThat(initialState.showErrorPassword).isFalse()
            assertThat(initialState.isButtonLoginEnable).isFalse()
            val userName = "test@example.com"
            //When
            viewModel.onEvents(LoginEvents.OnUserNameChange(userName))
            val secondState = awaitItem()
            val thirdState = awaitItem()
            assertThat(secondState.userName).isEqualTo(userName)
            assertThat(thirdState.isButtonLoginEnable).isTrue()
        }
    }

    @Test
    fun onUserNameChange_whenEmailValidPasswordInvalid_shouldShowPasswordError() = runTest {
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
            //When
            viewModel.onEvents(LoginEvents.OnUserNameChange(userName))
            val secondEmission = awaitItem()
            val thirdEmission = awaitItem()
            val lastEmission = awaitItem()
            //Then
            assertThat(secondEmission.userName).isEqualTo(userName)
            assertThat(thirdEmission.showErrorPassword).isTrue()
            assertThat(lastEmission.isButtonLoginEnable).isTrue()
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
            //When
            viewModel.onEvents(LoginEvents.OnUserNameChange(userName))
            val secondState = awaitItem()
            val thirdState = awaitItem()
            //Then
            assertThat(secondState.userName).isEqualTo(userName)
            assertThat(secondState.showErrorUserName).isFalse()
            assertThat(secondState.showErrorPassword).isFalse()
            assertThat(thirdState.showErrorUserName).isTrue()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onLoginClick_whenLoginIsSuccess() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(email = any(), password = any())
        } returns ApiResult.Success(
            Unit
        )

        val initialState = viewModel.state.value
        assertThat(initialState.isLoading).isFalse()

        viewModel.onEvents(LoginEvents.OnLoginClick)

        advanceUntilIdle()

        verify(exactly = 1) { loginPreferences.saveUserSession() }
        verify(exactly = 1) { loginPreferences.saveUserEmail(any()) }

        viewModel.effects.test {
            val effect = awaitItem()
            assert(effect is LoginSideEffects.NavigateToHomeScreen)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onLoginClick_whenLoginFails() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(email = any(), password = any())
        } returns ApiResult.Error(
            error = "Invalid credentials"
        )

        val initialState = viewModel.state.value
        assertThat(initialState.isLoading).isFalse()
        assertThat(initialState.showDialogPasswordOrEmailWrong).isFalse()

        viewModel.onEvents(LoginEvents.OnLoginClick)

        advanceUntilIdle()

        verify(exactly = 0) { loginPreferences.saveUserSession() }
        verify(exactly = 0) { loginPreferences.saveUserEmail(any()) }

        val finalState = viewModel.state.value
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.showDialogPasswordOrEmailWrong).isTrue()
        assertThat(finalState.isLoading).isFalse()
    }

}