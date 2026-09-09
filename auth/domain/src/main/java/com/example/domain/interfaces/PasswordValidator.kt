package com.example.domain.interfaces

interface PasswordValidator {
    fun isValidPassword(password: String): Boolean
}
