package com.example.citassalon.presentacion.features.auth.sign_up

import app.cash.turbine.test
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
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

class SignUpViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SignUpViewModel
    private val authRepository: AuthRepository = mockk()
    private val useCaseValidateForm: ValidateFormSignUpUseCase = mockk()

    companion object {
        const val ACCOUNT_CREATION_ERROR_MESSAGE = "Error creating account"
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel(
            authRepository = authRepository,
            useCaseValidateForm = useCaseValidateForm,
            ioDispatcher = testDispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    private fun mockValidationResult(
        isValidPhone: Boolean = true,
        isValidEmail: Boolean = true,
        isValidPassword: Boolean = true,
        hasEmptyFields: Boolean = false
    ) {
        every {
            useCaseValidateForm.invoke(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns ValidateFormSignUpUseCase.FormValidationResult(
            isValidPhone = isValidPhone,
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword,
            hasEmptyFields = hasEmptyFields
        )
    }

    @Test
    fun onNameChange_whenFormIsValid_shouldEnableButton() = runTest(testDispatcher) {

        val testName = "John Doe"

        mockValidationResult(
            isValidPhone = true,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnNameChange(testName))

            awaitItem()

            val state = awaitItem()

            assertThat(state.isEnableButton).isTrue()
            assertThat(state.showErrorPhone).isFalse()
            assertThat(state.showErrorEmail).isFalse()
            assertThat(state.showErrorPassword).isFalse()
        }
    }

    @Test
    fun onNameChange_whenFormIsInvalid_shouldDisableButton() = runTest(testDispatcher) {

        val testName = "John Doe"

        mockValidationResult(
            isValidPhone = false,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = false
        )


        viewModel.state.test {

            awaitItem()


            viewModel.onEvents(SingUpEvents.OnNameChange(testName))

            awaitItem()

            val state = awaitItem()

            assertThat(state.isEnableButton).isFalse()
            assertThat(state.showErrorPhone).isTrue()
            assertThat(state.showErrorEmail).isFalse()
            assertThat(state.showErrorPassword).isFalse()

        }
    }

    @Test
    fun onPhoneChange_whenPhoneIsValid_shouldNotShowPhoneError() = runTest(testDispatcher) {
        val testPhone = "1234567890"


        mockValidationResult(
            isValidPhone = true,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnPhoneChange(testPhone))

            awaitItem()

            val state = awaitItem()

            assertThat(state.isEnableButton).isTrue()
            assertThat(state.showErrorPhone).isFalse()
            assertThat(state.showErrorEmail).isFalse()
            assertThat(state.showErrorPassword).isFalse()

        }
    }

    @Test
    fun onPhoneChange_whenPhoneIsInvalid_shouldShowPhoneError() = runTest(testDispatcher) {
        val testPhone = "123"

        mockValidationResult(
            isValidPhone = false,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = false
        )


        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnPhoneChange(testPhone))

            awaitItem()

            val state = awaitItem()

            assertThat(state.isEnableButton).isFalse()
            assertThat(state.showErrorPhone).isTrue()
            assertThat(state.showErrorEmail).isFalse()
            assertThat(state.showErrorPassword).isFalse()

        }
    }

    @Test
    fun onEmailChange_whenEmailIsValid_shouldNotShowEmailError() = runTest(testDispatcher) {
        val testEmail = "john@example.com"

        mockValidationResult(
            isValidPhone = true,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnEmailChange(testEmail))

            awaitItem()

            val state = awaitItem()

            assertThat(state.isEnableButton).isTrue()
            assertThat(state.showErrorPhone).isFalse()
            assertThat(state.showErrorEmail).isFalse()
            assertThat(state.showErrorPassword).isFalse()

        }
    }

    @Test
    fun onEmailChange_whenEmailIsInvalid_shouldShowEmailError() = runTest(testDispatcher) {
        val testEmail = "invalid-email"

        mockValidationResult(
            isValidPhone = true,
            isValidEmail = false,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnEmailChange(testEmail))

            awaitItem()

            val state = awaitItem()
            assertThat(state.isEnableButton).isFalse()
            assertThat(state.showErrorPhone).isFalse()
            assertThat(state.showErrorEmail).isTrue()
            assertThat(state.showErrorPassword).isFalse()

        }
    }

    @Test
    fun onEmailChange_whenEmailIsInvalid_shouldDisableButton() = runTest(testDispatcher) {
        val testEmail = "invalid-email"

        mockValidationResult(
            isValidPhone = true,
            isValidEmail = false,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnEmailChange(testEmail))

            awaitItem()

            val state = awaitItem()

            assertThat(state.isEnableButton).isFalse()
        }
    }

    @Test
    fun onPasswordChange_whenPasswordIsValid_shouldNotShowPasswordError() =
        runTest(testDispatcher) {
            val testPassword = "StrongPass123"

            mockValidationResult(
                isValidPhone = true,
                isValidEmail = true,
                isValidPassword = true,
                hasEmptyFields = false
            )

            viewModel.state.test {

                awaitItem()

                viewModel.onEvents(SingUpEvents.OnPasswordChange(testPassword))

                awaitItem()

                val state = awaitItem()

                assertThat(state.isEnableButton).isTrue()
                assertThat(state.showErrorPassword).isFalse()
            }
        }

    @Test
    fun onPasswordChange_whenPasswordIsTooShort_shouldShowPasswordError() =
        runTest(testDispatcher) {
            val testPassword = "123"

            mockValidationResult(
                isValidPhone = true,
                isValidEmail = true,
                isValidPassword = false,
                hasEmptyFields = false
            )

            viewModel.state.test {

                awaitItem()

                viewModel.onEvents(SingUpEvents.OnPasswordChange(testPassword))

                awaitItem()

                val state = awaitItem()

                assertThat(state.isEnableButton).isFalse()
                assertThat(state.showErrorPassword).isTrue()

            }
        }

    @Test
    fun onPasswordChange_whenPasswordIsInvalid_shouldDisableButton() = runTest(testDispatcher) {
        val testPassword = "weak"

        mockValidationResult(
            isValidPhone = true,
            isValidEmail = true,
            isValidPassword = false,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnPasswordChange(testPassword))

            awaitItem()

            val state = awaitItem()
            assertEquals(false, state.isEnableButton)
        }
    }

    @Test
    fun onDateSelected_whenDateIsSelected_shouldUpdateBirthdayAndValidateForm() =
        runTest(testDispatcher) {
            val testDate = "2020-01-01"

            mockValidationResult(
                isValidPhone = true,
                isValidEmail = true,
                isValidPassword = true,
                hasEmptyFields = false
            )

            viewModel.state.test {

                awaitItem()

                viewModel.onEvents(SingUpEvents.OnDateSelected(testDate))

                awaitItem()

                val state = awaitItem()
                assertEquals(testDate, state.birthday)
                assertThat(state.isEnableButton).isTrue()
            }
        }

    @Test
    fun onOpenDatePick_shouldShowDatePicker() = runTest(testDispatcher) {
        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnOpenDatePick)

            val state = awaitItem()
            assertEquals(true, state.showDatePicker)
        }
    }

    @Test
    fun onCloseDatePicker_shouldHideDatePicker() = runTest(testDispatcher) {
        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnOpenDatePick)

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnCloseDatePicker)

            val state = awaitItem()
            assertEquals(false, state.showDatePicker)
        }
    }

    @Test
    fun validateForm_whenPhoneIsInvalid_shouldDisableButton() = runTest(testDispatcher) {
        mockValidationResult(
            isValidPhone = false,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {
            awaitItem()

            viewModel.onEvents(SingUpEvents.OnPhoneChange("123"))

            awaitItem()

            val state = awaitItem()
            assertEquals(false, state.isEnableButton)
            assertEquals(true, state.showErrorPhone)
        }
    }

    @Test
    fun validateForm_whenEmailIsInvalid_shouldDisableButton() = runTest(testDispatcher) {
        mockValidationResult(
            isValidPhone = true,
            isValidEmail = false,
            isValidPassword = true,
            hasEmptyFields = false
        )

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnEmailChange("invalid"))

            awaitItem()

            val state = awaitItem()
            assertEquals(false, state.isEnableButton)
            assertEquals(true, state.showErrorEmail)
        }
    }

    @Test
    fun validateForm_whenPasswordIsInvalid_shouldDisableButton() = runTest(testDispatcher) {
        mockValidationResult(
            isValidPhone = true,
            isValidEmail = true,
            isValidPassword = false,
            hasEmptyFields = false
        )

        viewModel.state.test {
            awaitItem()

            viewModel.onEvents(SingUpEvents.OnPasswordChange("123"))

            awaitItem()

            val state = awaitItem()
            assertEquals(false, state.isEnableButton)
            assertEquals(true, state.showErrorPassword)
        }
    }

    @Test
    fun validateForm_whenAnyFieldIsEmpty_shouldDisableButton() = runTest(testDispatcher) {
        mockValidationResult(
            isValidPhone = true,
            isValidEmail = true,
            isValidPassword = true,
            hasEmptyFields = true
        )

        viewModel.state.test {

            viewModel.onEvents(SingUpEvents.OnNameChange("orlando"))

            awaitItem()

//            awaitItem()  // Emission from resetErrorsInputs

            val state = awaitItem()  // Emission from validateForm result
            assertEquals(false, state.isEnableButton)
        }
    }

    @Test
    fun onSignUpClick_whenSignUpIsSuccess_shouldNavigateToLoginScreen() =
        runTest(testDispatcher) {

            coEvery {
                authRepository.register(
                    any(),
                    any()
                )
            } returns ApiResult.Success(Unit)

            every { authRepository.getUser() } returns ApiResult.Success(null)


            viewModel.effects.test {

                viewModel.onEvents(SingUpEvents.OnSignUpClick)

                val navigationEffect = awaitItem()

                val snackBarEffect = awaitItem()

                assertThat(navigationEffect).isInstanceOf(SignUpSideEffects.NavigateToLoginScreen::class.java)

                assertThat(snackBarEffect).isInstanceOf(SignUpSideEffects.ShowSnackBar::class.java)

            }
        }

    @Test
    fun onSignUpClick_whenSignUpFails_shouldShowErrorSnackBar() = runTest(testDispatcher) {

        coEvery {
            authRepository.register(
                any(),
                any()
            )
        } returns ApiResult.Error(ACCOUNT_CREATION_ERROR_MESSAGE)

        viewModel.effects.test {


            viewModel.onEvents(SingUpEvents.OnSignUpClick)

            val effect = awaitItem()

            assertThat(effect).isInstanceOf(SignUpSideEffects.ShowSnackBar::class.java)


            val snackBarEffect = effect as SignUpSideEffects.ShowSnackBar

            assertThat(snackBarEffect.message).contains(ACCOUNT_CREATION_ERROR_MESSAGE)

        }

    }


    @Test
    fun onSignUpClick_whenSignUpFails_shouldShowChange() = runTest(testDispatcher) {

        coEvery {
            authRepository.register(
                any(),
                any()
            )
        } returns ApiResult.Error(ACCOUNT_CREATION_ERROR_MESSAGE)

        viewModel.state.test {

            awaitItem()

            viewModel.onEvents(SingUpEvents.OnSignUpClick)

            val loadingEmission = awaitItem()
            assertEquals(true, loadingEmission.isLoading)

            val errorEmission = awaitItem()
            assertThat(errorEmission.error).isInstanceOf(Exception::class.java)
            assertThat(errorEmission.error?.message).isEqualTo(ACCOUNT_CREATION_ERROR_MESSAGE)

            val loadingFalseEmission = awaitItem()
            assertThat(loadingFalseEmission.isLoading).isFalse()

        }

    }

    @Test
    fun onSignUpClick_whenSignUpIsSuccess_shouldShowSuccessMessage() = runTest(testDispatcher) {

        coEvery {
            authRepository.register(
                any(),
                any()
            )
        } returns ApiResult.Success(Unit)


        every { authRepository.getUser() } returns ApiResult.Success(null)

        viewModel.effects.test {

            viewModel.onEvents(SingUpEvents.OnSignUpClick)


            var foundSuccess = false
            repeat(2) {
                val effect = awaitItem()
                if (effect is SignUpSideEffects.ShowSnackBar && effect.message == "Success") {
                    foundSuccess = true
                }
            }
            assertEquals(true, foundSuccess)
        }
    }

}
