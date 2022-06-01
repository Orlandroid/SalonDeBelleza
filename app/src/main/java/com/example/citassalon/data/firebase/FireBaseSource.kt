package com.example.citassalon.data.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FireBaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    fun getUser() = firebaseAuth.currentUser

    fun login(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun registrer(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun logout() = firebaseAuth.signOut()


    fun forgetPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)

    fun signInWithCredential(credential: AuthCredential) =
        firebaseAuth.signInWithCredential(credential)

}