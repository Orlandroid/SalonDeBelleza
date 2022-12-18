package com.example.citassalon.data.remote

import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.domain.LocalDataSource
import com.example.citassalon.domain.RemoteDataSource
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun addAppointment(appointment: Appointment) = localDataSource.addAppointment(appointment)

    suspend fun addManyAppointment(appointment: List<Appointment>) = localDataSource.addManyAppointment(appointment)

    suspend fun getAllAppointment(): List<Appointment> = localDataSource.getAllAppointment()


    suspend fun updateAppointment(appointment: Appointment) = localDataSource.updateAppointment(appointment)

    suspend fun deleteAppointment(appointment: Appointment): Int = localDataSource.deleteAppointment(appointment)

    suspend fun deleteAllAppointment() = localDataSource.deleteAllAppointment()

    suspend fun addProduct(productDb: ProductDb) = localDataSource.addProduct(productDb)

    suspend fun deleteAllProducts() = localDataSource.deleteAllProducts()

    fun getAllProducts() = localDataSource.getAllProducts()

    suspend fun getProducts(category: String) = remoteDataSource.getProducts(category)

    suspend fun getSingleProduct(id: Int) = remoteDataSource.getSingleProduct(id)

    suspend fun getCategories() = remoteDataSource.getCategories()

    suspend fun getSingleCart(id: Int) = remoteDataSource.getSingleCart(id)

    suspend fun getSucursales() = remoteDataSource.getSucursales()

    suspend fun getStaffs() = remoteDataSource.getStaffs()

    suspend fun getServices() = remoteDataSource.getServices()

    fun getUser() = remoteDataSource.getUser()

    fun login(email: String, password: String) = remoteDataSource.login(email, password)

    fun register(email: String, password: String) = remoteDataSource.register(email, password)

    fun forgetPassword(email: String) = remoteDataSource.forgetPassword(email)

    fun signInWithCredential(credential: AuthCredential) = remoteDataSource.signInWithCredential(credential)

    fun logout() = remoteDataSource.logout()


}