package com.example.citassalon.data.firebase

import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val fireBaseSource: FireBaseSource) {

    fun getUser() = fireBaseSource.getUser()

    fun login(email: String, password: String) = fireBaseSource.login(email, password)

    fun registrer(email: String, password: String) = fireBaseSource.registrer(email, password)
}