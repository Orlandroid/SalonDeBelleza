package com.example.domain

interface UserPreferences {
    suspend fun saveUserEmail(email: String)
    suspend fun removeUserEmail()
    suspend fun getUserEmail(): String?

    suspend fun saveUserLogged()
    suspend fun destroyUserSession()
    suspend fun isUserLoggedIn(): Boolean

    suspend fun saveRandomUserResponse(randomUserResponse: String)
    suspend fun removeRandomUserResponse()

    suspend fun saveUserMoney(userMoney: Double)
    suspend fun removeUserMoney()
    suspend fun getUserMoney(): Double
}