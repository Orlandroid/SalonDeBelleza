package com.example.domain


interface PasswordValidator {
    fun isValidPassword(password: String): Boolean
}


class MainPasswordValidator : PasswordValidator {
    override fun isValidPassword(password: String): Boolean {
        val passwordLength = password.trim().length
        return passwordLength > 8
    }
}