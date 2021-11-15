package com.example.citassalon.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class LoginPeferences @Inject constructor(sharedPreferences: SharedPreferences) :
    PreferencesManager(sharedPreferences) {


    fun saveUserSession() {
        savePreferenceKey(USER_LOEGED, true)
    }

    fun destroyUserSession() {
        removePreferenceKey(USER_LOEGED)
    }


    fun getUserSession(): Boolean {
        return preferences.getBoolean(USER_LOEGED, false)
    }


    fun saveUserEmail(userEmail: String) {
        savePreferenceKey(USER_EMAIL, userEmail)
    }

    fun getUserEmail(): String? {
        return preferences.getString(USER_EMAIL, "")
    }

}