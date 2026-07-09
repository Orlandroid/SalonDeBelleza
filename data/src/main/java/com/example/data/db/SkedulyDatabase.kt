package com.example.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.daos.AppointmentDao
import com.example.data.db.daos.CategoriesDao
import com.example.data.db.daos.ProductDao
import com.example.domain.entities.db.CategoryDb
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.db.AppointmentDb


@Database(
    entities = [AppointmentDb::class, ProductDb::class, CategoryDb::class],
    version = 6,
    exportSchema = false
)

abstract class SkedulyDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    abstract fun productDao(): ProductDao

    abstract fun categoryDao(): CategoriesDao
}