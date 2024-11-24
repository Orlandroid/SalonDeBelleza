package com.example.citassalon.presentacion.util

import android.text.TextUtils
import android.util.Patterns


fun isValidEmail(target: CharSequence): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
