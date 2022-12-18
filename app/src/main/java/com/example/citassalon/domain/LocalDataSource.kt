package com.example.citassalon.domain

import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.models.local.Appointment
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {

    suspend fun addProduct(productDb: ProductDb): Long

    suspend fun deleteAllProducts()

    fun getAllProducts(): Flow<List<ProductDb>>

    suspend fun addAppointment(appointment: Appointment)

    suspend fun addManyAppointment(appointment: List<Appointment>)

    suspend fun getAllAppointment(): List<Appointment>

    suspend fun updateAppointment(appointment: Appointment)

    suspend fun deleteAppointment(appointment: Appointment): Int

    suspend fun deleteAllAppointment()


}