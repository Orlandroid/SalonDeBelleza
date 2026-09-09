package com.example.domain.interfaces

interface EmailValidator {
    fun isValidEmail(email: String): Boolean
}