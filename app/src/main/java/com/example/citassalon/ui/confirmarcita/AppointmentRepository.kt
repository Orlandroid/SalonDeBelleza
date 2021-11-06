package com.example.citassalon.ui.confirmarcita

import com.example.citassalon.data.models.Appointment
import com.example.citassalon.data.room.AppointmentDao

class AppointmentRepository constructor(private val db: AppointmentDao) {

    suspend fun addAppointment(appointment: Appointment) {
        db.insertAppointment(appointment)
    }

    suspend fun addManyAppointment(appointment: List<Appointment>) {
        db.insertManyAppointment(appointment)
    }

    suspend fun getAllAppointment(): List<Appointment> {
        return db.getAllAppointment()
    }

    suspend fun updateAppointment(appointment: Appointment) {
        db.updateAppointment(appointment)
    }

    suspend fun deleteAppointment(appointment: Appointment) {
        db.deleteAppointment(appointment)
    }

    suspend fun deleteAllAppointment() {
        db.deleteAll()
    }

}