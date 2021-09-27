package com.example.citassalon.data.models.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StaffName:: class), version = 1,exportSchema = false)
abstract class StaffDatabase: RoomDatabase() {

    abstract fun staffDao(): StaffDao

    companion object {
        @Volatile
        private var Instance: StaffDatabase? = null

        const val DB_NAME = "Staff_DB"

        fun getInstance(context: Context) : StaffDatabase?{
            if(Instance==null){

                synchronized(StaffDatabase::class){
                    Room.databaseBuilder(
                        context.applicationContext,
                        StaffDatabase::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { Instance = it }
                }
            }

            return Instance
        }
    }
    abstract fun StaffDao(): StaffDao
}