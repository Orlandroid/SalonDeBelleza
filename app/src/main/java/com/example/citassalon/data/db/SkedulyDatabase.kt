package com.example.citassalon.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.citassalon.data.db.daos.AppointmentDao
import com.example.citassalon.data.db.daos.CategoriesDao
import com.example.citassalon.data.db.daos.ProductDao
import com.example.citassalon.data.db.entities.CategoryDb
import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.models.local.Appointment


@Database(
    entities = [Appointment::class, ProductDb::class, CategoryDb::class],
    version = 5,
    exportSchema = false
)

abstract class SkedulyDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    abstract fun productDao(): ProductDao

    abstract fun categoryDao(): CategoriesDao
}