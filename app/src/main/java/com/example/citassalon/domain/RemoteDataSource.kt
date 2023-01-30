package com.example.citassalon.domain

import com.example.citassalon.data.models.remote.Cart
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.data.models.remote.migration.SucursalesResponse
import com.example.citassalon.data.models.remote.ramdomuser.RandomUserResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import retrofit2.http.Url


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
}