package com.example.data.remote.auth


import com.example.data.firebase.FireBaseSource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImp(
    private val fireBaseSource: FireBaseSource
) : AuthRepository {
    override fun getUser(): FirebaseUser? = fireBaseSource.getUser()

    override fun login(
        email: String,
        password: String
    ) = fireBaseSource.login(email = email, password = password)

    override fun register(
        email: String,
        password: String
    ) = fireBaseSource.register(email = email, password = password)

    override fun forgetPassword(email: String) = fireBaseSource.forgetPassword(email = email)

    override fun signInWithCredential(credential: AuthCredential) =
        fireBaseSource.signInWithCredential(credential = credential)

    override fun logout() = fireBaseSource.logout()


}