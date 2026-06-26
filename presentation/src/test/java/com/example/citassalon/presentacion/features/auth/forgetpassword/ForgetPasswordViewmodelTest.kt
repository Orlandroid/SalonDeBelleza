package com.example.citassalon.presentacion.features.auth.forgetpassword

import app.cash.turbine.test
import com.example.domain.validation.EmailValidator
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.ApiResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ForgetPasswordViewmodelTest {

    private lateinit var viewModel: ForgetPasswordViewmodel
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = StandardTestDispatcher()
    private val emailValidator = mockk<EmailValidator>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        authRepository = mockk(relaxed = true)
        viewModel = ForgetPasswordViewmodel(
            authRepository,
            ioDispatcher = testDispatcher,
            emailValidator = emailValidator
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `initial state should have correct default values`() = runTest {
        val initialState = viewModel.state.value

        assertEquals(null, initialState.userEmail)
        assertEquals(false, initialState.enableButton)
        assertEquals(false, initialState.showErrorInvalidEmail)
        assertEquals(false, initialState.isLoading)
    }

    @Test
    fun onResetPassword_whenRepositoryReturnsSuccess_shouldUpdateLoadingAndShowEffect() =
        runTest(testDispatcher) {

            val messageSnackBar = "Password successful changed"

            coEvery { authRepository.forgetPassword(any()) } returns ApiResult.Success(Unit)

            viewModel.state.test {

                assertEquals(false, awaitItem().isLoading)

                viewModel.onEvents(
                    ForgetPasswordViewmodel.ForgetPasswordEvents.OnResetPassword
                )

                assertEquals(true, awaitItem().isLoading)


                assertEquals(false, awaitItem().isLoading)

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.effects.test {
                val effect = awaitItem()

                assert(effect is ForgetPasswordViewmodel.ForgetPasswordEffects.ShowSnackBar)

                assertEquals(
                    messageSnackBar,
                    (effect as ForgetPasswordViewmodel.ForgetPasswordEffects.ShowSnackBar).message
                )

                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) {
                authRepository.forgetPassword(any())
            }
        }

    @Test
    fun onResetPassword_whenRepositoryReturnsError_shouldUpdateLoadingAndShowErrorEffect() =
        runTest(testDispatcher) {

            val errorMessage = "Error"
            val snackBarMessage = "Error trying to updated password"

            coEvery {
                authRepository.forgetPassword(any())
            } returns ApiResult.Error(errorMessage)

            viewModel.effects.test {

                viewModel.state.test {

                    assertEquals(false, awaitItem().isLoading)

                    viewModel.onEvents(
                        ForgetPasswordViewmodel.ForgetPasswordEvents.OnResetPassword
                    )

                    assertEquals(true, awaitItem().isLoading)

                    assertEquals(false, awaitItem().isLoading)

                    cancelAndIgnoreRemainingEvents()
                }

                val effect = awaitItem()

                assert(effect is ForgetPasswordViewmodel.ForgetPasswordEffects.ShowSnackBar)

                assertEquals(
                    snackBarMessage,
                    (effect as ForgetPasswordViewmodel.ForgetPasswordEffects.ShowSnackBar).message
                )

                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) {
                authRepository.forgetPassword(any())
            }
        }

    @Test
    fun onValidEmail_whenValidEmailProvided_shouldEnableButton() {
        coEvery { emailValidator.isValidEmail(any()) } returns true

        viewModel.onEvents(ForgetPasswordViewmodel.ForgetPasswordEvents.OnEmailChange("test@example.com"))

        val updatedState = viewModel.state.value
        assertEquals(true, updatedState.enableButton)
        assertEquals(false, updatedState.showErrorInvalidEmail)
    }

    @Test
    fun onValidEmail_whenInvalidEmailProvided_shouldDisableButton() {
        coEvery { emailValidator.isValidEmail(any()) } returns false

        viewModel.onEvents(ForgetPasswordViewmodel.ForgetPasswordEvents.OnEmailChange("invalid-email"))

        val updatedState = viewModel.state.value
        assertEquals(false, updatedState.enableButton)
        assertEquals(true, updatedState.showErrorInvalidEmail)
    }

}
