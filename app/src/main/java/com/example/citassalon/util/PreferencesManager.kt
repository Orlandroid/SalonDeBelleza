package com.example.citassalon.util

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesManager @Inject constructor(private val preferences: SharedPreferences) {

    companion object {
        const val USER_EMAIL = "email"
    }

    fun saveUserEmail(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_EMAIL, token)
        editor.apply()
    }


    fun getUserEmail(): String? {
        return preferences.getString(USER_EMAIL, "")
    }

}