package com.example.domain.repository

import com.example.domain.state.ApiResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    fun getUser(): ApiResult<FirebaseUser?>

    suspend fun login(email: String, password: String): ApiResult<Unit>

    suspend fun register(email: String, password: String): ApiResult<Unit>

    suspend fun forgetPassword(email: String): ApiResult<Unit>

    suspend fun signInWithCredential(credential: AuthCredential): ApiResult<Unit>

    suspend fun logout(): ApiResult<Unit>
}