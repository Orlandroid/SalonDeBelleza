package com.example.citassalon.data.repository

import com.example.citassalon.data.api.FakeStoreService
import com.example.citassalon.data.api.WebServices
import com.example.citassalon.data.db.AppointmentDao
import com.example.citassalon.data.db.ProductDao
import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.models.local.Appointment
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: AppointmentDao,
    private val productDao: ProductDao,
    private val webServices: WebServices,
    private val fakeStoreService: FakeStoreService,
    private val fireBaseSource: FireBaseSource
) {

    suspend fun addAppointment(appointment: Appointment) {
        db.insertAppointment(appointment)
    }

    suspend fun addManyAppointment(appointment: List<Appointment>) {
        db.insertManyAppointment(appointment)
    }

    suspend fun getAllAppointment(): List<Appointment> {
        return db.getAllAppointment()
    }

    suspend fun updateAppointment(appointment: Appointment) {
        db.updateAppointment(appointment)
    }

    suspend fun deleteAppointment(appointment: Appointment): Int {
        return db.deleteAppointment(appointment)
    }

    suspend fun deleteAllAppointment() {
        db.deleteAll()
    }

    suspend fun addProduct(productDb: ProductDb) = productDao.addProductDb(productDb)

    suspend fun deleteAllProducts() = productDao.deleteAll()

    fun getAllProducts() = productDao.getAllProductDb()

    suspend fun getProducts(categoria: String) = fakeStoreService.getProducts(categoria)

    suspend fun getSingleProduct(id: Int) = fakeStoreService.getSingleProduct(id)

    suspend fun getCategories() = fakeStoreService.getCategories()

    suspend fun getAllCarts() = fakeStoreService.getAllCarts()

    suspend fun getSingleCart(id: Int) = fakeStoreService.getSingleCart(id)

    suspend fun getSucursales() = webServices.getSucursales()

    suspend fun getStaffs() = webServices.getStaff()

    suspend fun getAppointMents() = webServices.getAppointMents()

    suspend fun getServices() = webServices.getServicios()

    fun getUser() = fireBaseSource.getUser()

    fun login(email: String, password: String) = fireBaseSource.login(email, password)

    fun registrer(email: String, password: String) = fireBaseSource.registrer(email, password)

    fun forgetPassword(email: String) = fireBaseSource.forgetPassword(email)

    fun signInWithCredential(credential: AuthCredential) =
        fireBaseSource.signInWithCredential(credential)

    fun logout() = fireBaseSource.logout()


}