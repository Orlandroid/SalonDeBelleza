package com.example.citassalon.presentacion.features.auth.sign_up

import com.example.citassalon.presentacion.util.EmailValidator
import com.google.common.truth.Truth.assertThat
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

class UseCaseValidateFormSignUpTest {

    private lateinit var useCaseValidateFormSignUp: UseCaseValidateFormSignUp
    private val testDispatcher = StandardTestDispatcher()
    private val emailValidator: EmailValidator = mockk()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCaseValidateFormSignUp = UseCaseValidateFormSignUp(
            emailValidator = emailValidator
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun invoke_whenAllFieldsAreValid_shouldReturnFormValid() = runTest {
        every { emailValidator.isValidEmail("john@example.com") } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John Doe",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }


    @Test
    fun invoke_whenPhoneIsValid_shouldReturnValidPhone() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }

    @Test
    fun invoke_whenPhoneIsTooShort_shouldReturnInvalidPhone() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "123456789"
        )

        assertThat(result.isValidPhone).isFalse()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenPhoneIsTooLong_shouldReturnInvalidPhone() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "12345678901"  // 11 characters
        )

        assertThat(result.isValidPhone).isFalse()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenPhoneIsEmpty_shouldReturnInvalidPhoneAndEmptyFields() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = ""
        )

        assertThat(result.isValidPhone).isFalse()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenPhoneHasLeadingTrailingSpaces_shouldTrimBeforeValidation() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "  1234567890  "  // 10 chars after trim
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }


    @Test
    fun invoke_whenEmailIsValid_shouldReturnValidEmail() = runTest {
        every { emailValidator.isValidEmail("john@example.com") } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }

    @Test
    fun invoke_whenEmailIsInvalid_shouldReturnInvalidEmail() = runTest {
        every { emailValidator.isValidEmail("invalid-email") } returns false

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "invalid-email",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isFalse()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenEmailIsEmpty_shouldReturnInvalidEmailAndEmptyFields() = runTest {
        every { emailValidator.isValidEmail("") } returns false

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isFalse()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }


    @Test
    fun invoke_whenPasswordIsValid_shouldReturnValidPassword() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",  // 13 characters (> 8)
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }

    @Test
    fun invoke_whenPasswordIsExactly9Characters_shouldReturnValidPassword() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "123456789",  // Exactly 9 characters (> 8)
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }

    @Test
    fun invoke_whenPasswordIsExactly8Characters_shouldReturnInvalidPassword() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "12345678",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isFalse()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenPasswordIsTooShort_shouldReturnInvalidPassword() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "short",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isFalse()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenPasswordIsEmpty_shouldReturnInvalidPasswordAndEmptyFields() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isFalse()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenPasswordHasLeadingTrailingSpaces_shouldTrimBeforeValidation() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "  StrongPass123  ",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isTrue()
    }


    @Test
    fun invoke_whenNameIsEmpty_shouldReturnEmptyFields() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenBirthDayIsEmpty_shouldReturnEmptyFields() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "",
            name = "John",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenMultipleFieldsAreEmpty_shouldReturnEmptyFields() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "",
            name = "",
            password = "",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isFalse()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenNameHasOnlySpaces_shouldReturnEmptyFields() = runTest {
        every { emailValidator.isValidEmail(any()) } returns true

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "   ",
            password = "StrongPass123",
            email = "john@example.com",
            phone = "1234567890"
        )

        assertThat(result.isValidPhone).isTrue()
        assertThat(result.isValidEmail).isTrue()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }


    @Test
    fun invoke_whenPhoneAndEmailAreInvalid_shouldReturnBothInvalid() = runTest {
        every { emailValidator.isValidEmail("invalid-email") } returns false

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "1990-01-01",
            name = "John",
            password = "StrongPass123",
            email = "invalid-email",
            phone = "123"
        )

        assertThat(result.isValidPhone).isFalse()
        assertThat(result.isValidEmail).isFalse()
        assertThat(result.isValidPassword).isTrue()
        assertThat(result.hasEmptyFields).isFalse()
        assertThat(result.isFormValid).isFalse()
    }

    @Test
    fun invoke_whenAllFieldsAreInvalid_shouldReturnFormInvalid() = runTest {
        every { emailValidator.isValidEmail("invalid") } returns false

        val result = useCaseValidateFormSignUp.invoke(
            birthDay = "",
            name = "",
            password = "short",
            email = "invalid",
            phone = "123"
        )

        assertThat(result.isValidPhone).isFalse()
        assertThat(result.isValidEmail).isFalse()
        assertThat(result.isValidPassword).isFalse()
        assertThat(result.hasEmptyFields).isTrue()
        assertThat(result.isFormValid).isFalse()
    }

}