package com.example.citassalon.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.citassalon.data.models.local.Appointment


@Database(entities = [Appointment::class], version = 1, exportSchema = false)

abstract class SkedulyDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao
}