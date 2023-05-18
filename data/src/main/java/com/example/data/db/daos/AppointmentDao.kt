package com.example.data.db.daos

import androidx.room.*
import com.example.domain.entities.db.Appointment

@Dao
interface AppointmentDao {

    @Insert
    suspend fun insertAppointment(appointment: Appointment)

    @Insert
    suspend fun insertManyAppointment(appointment: List<Appointment>)

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment):Int

    @Query("SELECT * FROM appointment")
    fun getAllAppointment(): List<Appointment>

    @Query("DELETE  FROM appointment")
    suspend fun deleteAll()

    @Query("SELECT * FROM appointment WHERE id =:id")
    suspend fun getAppointmentById(id: Int): Appointment

}