package com.example.citassalon.ui.staff

import com.example.citassalon.data.models.Appointment
import com.example.citassalon.data.room.AppointmentDao
import javax.inject.Inject

class StaffRepository @Inject constructor(private val db: AppointmentDao) {

    suspend fun addStaff(appointment: Appointment) {
        db.insertAppointment(appointment)
    }

    suspend fun addManyStaff(appointment: List<Appointment>) {
        db.insertManyAppointment(appointment)
    }

    suspend fun getAllStaff(): List<Appointment> {
        return db.getAllAppointment()
    }

    suspend fun updateStaff(appointment: Appointment) {
        db.updateAppointment(appointment)
    }

    suspend fun deleteStaff(appointment: Appointment) {
        db.deleteAppointment(appointment)
    }

    suspend fun deleteAllStaff() {
        db.deleteAll()
    }

}