package com.example.citassalon.presentacion.features.auth.sign_up


import com.example.citassalon.presentacion.util.isValidEmail
import javax.inject.Inject

class UseCaseValidateFormSignUp @Inject constructor() {

    data class FormValidationResult(
        val isValidPhone: Boolean,
        val isValidEmail: Boolean,
        val isValidPassword: Boolean,
        val hasEmptyFields: Boolean
    ) {
        val isFormValid: Boolean
            get() = isValidPhone && isValidEmail && isValidPassword && !hasEmptyFields
    }

    companion object {
        private const val MINIMAL_CHARACTERS_PASSWORD = 8
        private const val PHONE_NUMBER_CHARACTERS = 10
    }

    fun invoke(
        birthDay: String,
        name: String,
        password: String,
        email: String,
        phone: String
    ): FormValidationResult {
        var isValidPhone = true
        var isValidEmail = true
        var isValidPassword = true
        var areEmptyFields = false
        if (!isValidNumber(phone)) {
            isValidPhone = false
        }
        if (areEmptyFields(
                birthDay = birthDay,
                name = name,
                password = password,
                email = email,
                phone = phone
            )
        ) {
            areEmptyFields = true
        }
        if (!isValidPassword(password = password)) {
            isValidPassword = false
        }
        if (!isValidEmail(email)) {
            isValidEmail = false
        }
        return FormValidationResult(
            isValidPhone = isValidPhone,
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword,
            hasEmptyFields = areEmptyFields
        )
    }

    private fun isValidNumber(phone: String) = phone.trim().length == PHONE_NUMBER_CHARACTERS

    private fun isValidPassword(password: String): Boolean {
        val passwordLength = password.trim().length
        return passwordLength > MINIMAL_CHARACTERS_PASSWORD
    }

    private fun areEmptyFields(
        birthDay: String,
        name: String,
        password: String,
        email: String,
        phone: String
    ): Boolean {
        val nameIsEmpty = name.trim().isEmpty()
        val phoneIsEmpty = phone.trim().isEmpty()
        val emailIsEmpty = email.trim().isEmpty()
        val passwordIsEmpty = password.trim().isEmpty()
        val birthDayIsEmpty = birthDay.trim().isEmpty()
        return nameIsEmpty or phoneIsEmpty or emailIsEmpty or passwordIsEmpty or birthDayIsEmpty
    }

}