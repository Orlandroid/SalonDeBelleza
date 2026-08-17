package com.example.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.domain.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginPreferences @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesManager(context = context), UserPreferences {

    companion object {
        const val USER_EMAIL = "email"
        const val USER_LOGGED = "userLogged"
        const val RANDOM_USER_RESPONSE = "RandomUser"
        const val USER_MONEY = "userMoney"
    }

    override suspend fun saveUserEmail(email: String) {
        savePreferenceKey(USER_EMAIL, email)
    }

    override suspend fun removeUserEmail() {
        removePreferenceKey(USER_EMAIL)
    }

    override suspend fun getUserEmail(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(USER_EMAIL)]
        }.firstOrNull()
    }

    override suspend fun saveUserLogged() {
        savePreferenceKey(USER_LOGGED, true)
    }

    override suspend fun destroyUserSession() {
        removePreferenceKey(USER_LOGGED)
    }

    val userLogged = booleanPreferencesKey("userLogged")


    override suspend fun isUserLoggedIn(): Boolean {
        val userMoney = context.dataStore.data.map { preferences ->
            preferences[userLogged] ?: false
        }.firstOrNull() ?: false
        return userMoney
    }

    override suspend fun saveRandomUserResponse(randomUserResponse: String) {
        savePreferenceKey(RANDOM_USER_RESPONSE, randomUserResponse)
    }

    override suspend fun removeRandomUserResponse() {
        removePreferenceKey(RANDOM_USER_RESPONSE)
    }

    override suspend fun saveUserMoney(userMoney: Double) {
        savePreferenceKey(USER_MONEY, userMoney.toInt())
    }

    override suspend fun removeUserMoney() {
        removePreferenceKey(USER_MONEY)
    }

    val userMoneyKey = doublePreferencesKey(USER_MONEY)

    override suspend fun getUserMoney(): Double {
        val userMoney = context.dataStore.data.map { preferences ->
            preferences[userMoneyKey] ?: 0
        }.firstOrNull() ?: 0
        return userMoney.toDouble()
    }


}