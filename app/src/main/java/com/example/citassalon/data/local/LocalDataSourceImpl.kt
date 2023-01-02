package com.example.citassalon.data.local

import com.example.citassalon.data.db.daos.AppointmentDao
import com.example.citassalon.data.db.daos.CategoriesDao
import com.example.citassalon.data.db.daos.ProductDao
import com.example.citassalon.data.db.entities.CategoryDb
import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.domain.LocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val appointmentDao: AppointmentDao,
    private val productDao: ProductDao,
    private val categoriesDao: CategoriesDao
) : LocalDataSource {

    override suspend fun addProduct(productDb: ProductDb): Long = productDao.addProductDb(productDb)

    override suspend fun insertManyProductDb(productDb: List<ProductDb>) = productDao.insertManyProductDb(productDb)

    override suspend fun deleteAllProducts() = productDao.deleteAll()

    override fun getAllProducts() = productDao.getAllProductDb()

    override fun getAllProductDbCache(): List<ProductDb> = productDao.getAllProductDbCache()

    override suspend fun addAppointment(appointment: Appointment) = appointmentDao.insertAppointment(appointment)

    override suspend fun addManyAppointment(appointment: List<Appointment>) = appointmentDao.insertManyAppointment(appointment)

    override suspend fun getAllAppointment(): List<Appointment> = appointmentDao.getAllAppointment()

    override suspend fun updateAppointment(appointment: Appointment) = appointmentDao.updateAppointment(appointment)

    override suspend fun deleteAppointment(appointment: Appointment): Int  = appointmentDao.deleteAppointment(appointment)

    override suspend fun deleteAllAppointment() = appointmentDao.deleteAll()

    override suspend fun getCategoriesFromDb() = categoriesDao.getCategories()

    override suspend fun addManyCategories(categories: List<CategoryDb>) = categoriesDao.addManyCategories(categories)


}
