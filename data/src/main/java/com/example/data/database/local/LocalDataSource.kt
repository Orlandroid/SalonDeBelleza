package com.example.data.database.local

import com.example.data.database.entities.AppointmentEntity
import com.example.data.database.entities.CategoryEntity
import com.example.data.database.entities.ProductEntity

interface LocalDataSource {

    suspend fun addProduct(productDb: ProductEntity): Long

    suspend fun insertManyProductDb(productDb: List<ProductEntity>)

    suspend fun deleteAllProducts(): Int

    fun getAllProducts(): List<ProductEntity>


    suspend fun addAppointment(appointment: AppointmentEntity)

    suspend fun addManyAppointment(appointment: List<AppointmentEntity>)

    suspend fun getAllAppointment(): List<AppointmentEntity>

    suspend fun updateAppointment(appointment: AppointmentEntity)

    suspend fun deleteAppointment(appointment: AppointmentEntity): Int

    suspend fun deleteAllAppointment()

    suspend fun getCategoriesFromDb(): List<CategoryEntity>

    suspend fun addManyCategories(categories: List<CategoryEntity>)
}