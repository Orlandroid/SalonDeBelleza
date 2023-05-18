package com.example.domain



import com.example.domain.entities.db.AppointmentDb
import com.example.domain.entities.db.CategoryDb
import com.example.domain.entities.db.ProductDb
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {

    suspend fun addProduct(productDb: ProductDb): Long

    suspend fun insertManyProductDb(productDb: List<ProductDb>)

    suspend fun deleteAllProducts()

    fun getAllProducts(): Flow<List<ProductDb>>

    fun getAllProductDbCache(): List<ProductDb>

    suspend fun addAppointment(appointment: AppointmentDb)

    suspend fun addManyAppointment(appointment: List<AppointmentDb>)

    suspend fun getAllAppointment(): List<AppointmentDb>

    suspend fun updateAppointment(appointment: AppointmentDb)

    suspend fun deleteAppointment(appointment: AppointmentDb): Int

    suspend fun deleteAllAppointment()

    suspend fun getCategoriesFromDb(): List<CategoryDb>

    suspend fun addManyCategories(categories: List<CategoryDb>)
}