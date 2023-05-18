package com.example.domain


import com.example.domain.entities.remote.ramdomuser.RandomUserResponse
import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product
import com.example.domain.entities.remote.Servicio
import com.example.domain.entities.remote.Staff
import com.example.domain.entities.remote.dummyUsers.DummyUsersResponse
import com.example.domain.entities.remote.migration.SucursalesResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser


interface RemoteDataSource {

    suspend fun getProducts(category: String): List<Product>

    suspend fun getSingleProduct(id: Int): Product

    suspend fun getCategories(): List<String>

    suspend fun getSingleCart(id: Int): Cart

    suspend fun getSucursales(): SucursalesResponse

    suspend fun getStaffs(): List<Staff>

    suspend fun getServices(): List<Servicio>

    fun getUser(): FirebaseUser?

    fun login(email: String, password: String): Task<AuthResult>

    fun register(email: String, password: String): Task<AuthResult>

    fun forgetPassword(email: String): Task<Void>

    fun signInWithCredential(credential: AuthCredential): Task<AuthResult>

    fun logout()

    suspend fun randomUser(): RandomUserResponse

    suspend fun getStaffUsers(): DummyUsersResponse
}