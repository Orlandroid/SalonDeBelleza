package com.example.data.remote.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {

    fun getUser(): FirebaseUser?

    fun login(email: String, password: String): Task<AuthResult>

    fun register(email: String, password: String): Task<AuthResult>

    fun forgetPassword(email: String): Task<Void>

    fun signInWithCredential(credential: AuthCredential): Task<AuthResult>

    fun logout()
}