package com.example.data.database.daos

import androidx.room.*
import com.example.data.database.entities.AppointmentEntity

@Dao
interface AppointmentDao {

    @Insert
    suspend fun insertAppointment(appointment: AppointmentEntity)

    @Insert
    suspend fun insertManyAppointment(appointment: List<AppointmentEntity>)

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntity)

    @Delete
    suspend fun deleteAppointment(appointment: AppointmentEntity):Int

    @Query("SELECT * FROM appointmententity")
    fun getAllAppointment(): List<AppointmentEntity>

    @Query("DELETE  FROM appointmententity")
    suspend fun deleteAll()

    @Query("SELECT * FROM appointmententity WHERE id =:id")
    suspend fun getAppointmentById(id: Int): AppointmentEntity

}