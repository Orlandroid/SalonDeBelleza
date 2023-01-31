package com.example.citassalon.data

import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.mappers.toListCategoriesString
import com.example.citassalon.data.mappers.toStringList
import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.data.models.remote.ramdomuser.RandomUserResponse
import com.example.citassalon.domain.LocalDataSource
import com.example.citassalon.domain.RemoteDataSource
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun addAppointment(appointment: Appointment) =
        localDataSource.addAppointment(appointment)

    suspend fun addManyAppointment(appointment: List<Appointment>) =
        localDataSource.addManyAppointment(appointment)

    suspend fun getAllAppointment(): List<Appointment> = localDataSource.getAllAppointment()


    suspend fun updateAppointment(appointment: Appointment) =
        localDataSource.updateAppointment(appointment)

    suspend fun deleteAppointment(appointment: Appointment): Int =
        localDataSource.deleteAppointment(appointment)

    suspend fun deleteAllAppointment() = localDataSource.deleteAllAppointment()

    suspend fun addProduct(productDb: ProductDb) = localDataSource.addProduct(productDb)

    suspend fun deleteAllProducts() = localDataSource.deleteAllProducts()

    fun getAllProducts() = localDataSource.getAllProducts()

    suspend fun getProducts(category: String): List<Product> {
        /*
        val localProducts = localDataSource.getAllProductDbCache()
        val remoteProducts: List<Product>
        if (localProducts.isEmpty()){
             remoteProducts = remoteDataSource.getProducts(category)
            localDataSource.insertManyProductDb(remoteProducts.toProductDbtList())
            return remoteProducts
        }
        return localProducts.toProductList()*/
        return remoteDataSource.getProducts(category)
    }

    suspend fun getSingleProduct(id: Int) = remoteDataSource.getSingleProduct(id)

    suspend fun getCategories(): List<String> {
        val listOfCategoriesFromLocalSource = localDataSource.getCategoriesFromDb()
        return if (listOfCategoriesFromLocalSource.isEmpty()) {
            val categories = remoteDataSource.getCategories()
            localDataSource.addManyCategories(categories.toListCategoriesString())
            remoteDataSource.getCategories()
        } else {
            localDataSource.getCategoriesFromDb().toStringList()
        }
    }

    suspend fun getSingleCart(id: Int) = remoteDataSource.getSingleCart(id)

    suspend fun getSucursales() = remoteDataSource.getSucursales()

    suspend fun getStaffs() = remoteDataSource.getStaffs()

    suspend fun getServices() = remoteDataSource.getServices()

    fun getUser() = remoteDataSource.getUser()

    fun login(email: String, password: String) = remoteDataSource.login(email, password)

    fun register(email: String, password: String) = remoteDataSource.register(email, password)

    fun forgetPassword(email: String) = remoteDataSource.forgetPassword(email)

    fun signInWithCredential(credential: AuthCredential) =
        remoteDataSource.signInWithCredential(credential)

    fun logout() = remoteDataSource.logout()

    suspend fun randomUser(): RandomUserResponse = remoteDataSource.randomUser()

    suspend fun getStaffUsers() = remoteDataSource.getStaffUsers()

}