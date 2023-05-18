package com.example.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.daos.AppointmentDao
import com.example.data.db.daos.CategoriesDao
import com.example.data.db.daos.ProductDao
import com.example.domain.entities.db.CategoryDb
import com.example.domain.entities.db.ProductDb


@Database(
    entities = [com.example.domain.entities.db.Appointment::class, ProductDb::class, CategoryDb::class],
    version = 5,
    exportSchema = false
)

abstract class SkedulyDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    abstract fun productDao(): ProductDao

    abstract fun categoryDao(): CategoriesDao
}