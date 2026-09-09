package com.example.data

import android.text.TextUtils
import android.util.Patterns
import com.example.domain.interfaces.EmailValidator


class AndroidEmailValidator : EmailValidator {
    override fun isValidEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}