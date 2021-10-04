package com.example.citassalon.data.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.citassalon.data.models.Appointment


@Database(entities = [Appointment::class], version = 2, exportSchema = false)

abstract class SkedulyDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao
}