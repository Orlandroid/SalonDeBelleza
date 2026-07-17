package com.example.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.daos.AppointmentDao
import com.example.data.database.daos.CategoriesDao
import com.example.data.database.daos.ProductDao
import com.example.data.database.entities.CategoryEntity
import com.example.data.database.entities.ProductEntity
import com.example.data.database.entities.AppointmentEntity


@Database(
    entities = [AppointmentEntity::class, ProductEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class SkedulyDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    abstract fun productDao(): ProductDao

    abstract fun categoryDao(): CategoriesDao
}