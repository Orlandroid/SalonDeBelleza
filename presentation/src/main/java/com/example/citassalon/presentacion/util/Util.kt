package com.example.citassalon.presentacion.util

import android.text.TextUtils
import android.util.Patterns

interface EmailValidator {
    fun isValidEmail(email: String): Boolean
}

class AndroidEmailValidator : EmailValidator {
    override fun isValidEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}

fun isValidEmail(target: CharSequence): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
