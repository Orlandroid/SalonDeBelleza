package com.example.data.local


import com.example.data.database.daos.AppointmentDao
import com.example.data.database.daos.CategoriesDao
import com.example.data.database.daos.ProductDao
import com.example.data.database.entities.AppointmentEntity
import com.example.data.database.entities.CategoryEntity
import com.example.data.database.entities.ProductEntity
import com.example.data.database.local.LocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomLocalDataSource @Inject constructor(
    private val appointmentDao: AppointmentDao,
    private val productDao: ProductDao,
    private val categoriesDao: CategoriesDao
) : LocalDataSource {

    override suspend fun addProduct(productDb: ProductEntity): Long = productDao.addProductDb(productDb)

    override suspend fun insertManyProductDb(productDb: List<ProductEntity>) = productDao.insertManyProductDb(productDb)

    override suspend fun deleteAllProducts() = productDao.deleteAll()

    override fun getAllProducts() = productDao.getAllProductDb()

    override suspend fun addAppointment(appointment: AppointmentEntity) = appointmentDao.insertAppointment(appointment)

    override suspend fun addManyAppointment(appointment: List<AppointmentEntity>) = appointmentDao.insertManyAppointment(appointment)

    override suspend fun getAllAppointment(): List<AppointmentEntity> = appointmentDao.getAllAppointment()

    override suspend fun updateAppointment(appointment: AppointmentEntity) = appointmentDao.updateAppointment(appointment)

    override suspend fun deleteAppointment(appointment: AppointmentEntity): Int  = appointmentDao.deleteAppointment(appointment)

    override suspend fun deleteAllAppointment() = appointmentDao.deleteAll()

    override suspend fun getCategoriesFromDb() = categoriesDao.getCategories()

    override suspend fun addManyCategories(categories: List<CategoryEntity>) = categoriesDao.addManyCategories(categories)


}
