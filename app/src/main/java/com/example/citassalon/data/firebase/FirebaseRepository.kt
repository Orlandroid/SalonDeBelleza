package com.example.citassalon.data.firebase

import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val fireBaseSource: FireBaseSource) {

    fun getUser() = fireBaseSource.getUser()

    fun login(email: String, password: String) = fireBaseSource.login(email, password)

    fun registrer(email: String, password: String) = fireBaseSource.registrer(email, password)

    fun forgetPassword(email: String) = fireBaseSource.forgetPassword(email)

    fun signInWithCredential(credential: AuthCredential) =
        fireBaseSource.signInWithCredential(credential)


}