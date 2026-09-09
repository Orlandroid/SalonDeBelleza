package com.example.data

import com.example.domain.interfaces.PasswordValidator

class MainPasswordValidator : PasswordValidator {
    override fun isValidPassword(password: String): Boolean {
        val passwordLength = password.trim().length
        return passwordLength > 8
    }
}