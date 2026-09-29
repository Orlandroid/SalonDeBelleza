package com.example.data.auth

import android.content.Context
import com.example.data.preferences.SecurePreferencesManager
import com.example.domain.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginPreferences @Inject constructor(
    @ApplicationContext context: Context
) : SecurePreferencesManager(context = context), UserPreferences {

    companion object {
        const val USER_EMAIL = "email"
        const val USER_LOGGED = "userLogged"
        const val RANDOM_USER_RESPONSE = "RandomUser"
        const val USER_MONEY = "userMoney"
    }

    override suspend fun saveUserEmail(email: String) {
        saveSecureKey(USER_EMAIL, email)
    }

    override suspend fun removeUserEmail() {
        removeSecureKey(USER_EMAIL)
    }

    override suspend fun getUserEmail(): String {
        return getSecureString(USER_EMAIL)
    }

    override suspend fun saveUserLogged() {
        saveSecureKey(USER_LOGGED, true)
    }

    override suspend fun destroyUserSession() {
        removeSecureKey(USER_LOGGED)
    }


    override suspend fun isUserLoggedIn(): Boolean {
        return getSecureBoolean(USER_LOGGED)
    }

    override suspend fun saveRandomUserResponse(randomUserResponse: String) {
        saveSecureKey(RANDOM_USER_RESPONSE, randomUserResponse)
    }

    override suspend fun removeRandomUserResponse() {
        removeSecureKey(RANDOM_USER_RESPONSE)
    }


}