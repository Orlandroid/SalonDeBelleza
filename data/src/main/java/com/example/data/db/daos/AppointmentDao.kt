package com.example.data.db.daos

import androidx.room.*
import com.example.domain.entities.db.AppointmentDb

@Dao
interface AppointmentDao {

    @Insert
    suspend fun insertAppointment(appointment: AppointmentDb)

    @Insert
    suspend fun insertManyAppointment(appointment: List<AppointmentDb>)

    @Update
    suspend fun updateAppointment(appointment: AppointmentDb)

    @Delete
    suspend fun deleteAppointment(appointment: AppointmentDb):Int

    @Query("SELECT * FROM appointmentdb")
    fun getAllAppointment(): List<AppointmentDb>

    @Query("DELETE  FROM appointmentdb")
    suspend fun deleteAll()

    @Query("SELECT * FROM appointmentdb WHERE id =:id")
    suspend fun getAppointmentById(id: Int): AppointmentDb

}