package com.example.citassalon.data.preferences

import android.content.SharedPreferences
import com.example.citassalon.data.models.remote.ramdomuser.RandomUserResponse
import com.google.gson.Gson
import javax.inject.Inject

class LoginPeferences @Inject constructor(sharedPreferences: SharedPreferences) :
    PreferencesManager(sharedPreferences) {

    companion object {
        const val USER_EMAIL = "email"
        const val USER_LOEGED = "userLoged"
        const val RANDOM_USER_RESPONSE = "RandomUser"
        const val USER_MONEY = "userMoney"
    }

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

    fun saveUserRandomResponse(randomUser: RandomUserResponse) {
        Gson().toJson(randomUser)
        savePreferenceKey(RANDOM_USER_RESPONSE, randomUser)
        val postCode = randomUser.results[0].location.postcode
        val userMoney = postCode.toInt() / getLastNumberPostCode(postCode)
        savePreferenceKey(USER_MONEY, userMoney)
    }

    // 88742
    private fun getLastNumberPostCode(postCode: String):Int {
        return postCode[4].code
    }

    fun getUserRandomResponse(): RandomUserResponse? {
        val user = preferences.getString(RANDOM_USER_RESPONSE, null)
        return Gson().fromJson(user, RandomUserResponse::class.java)
    }

    fun getUserMoney(): Int {
        return preferences.getInt(USER_MONEY, 0)
    }

}