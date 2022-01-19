package com.example.citassalon.data.repository

import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.data.room.AppointmentDao
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: AppointmentDao,
    private val webServices: WebServices,
    private val fireBaseSource: FireBaseSource
) {

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


    suspend fun getSucursales() = webServices.getSucursales()

    suspend fun getStaffs() = webServices.getStaff()

    suspend fun getServices() = webServices.getServicios()

    fun getUser() = fireBaseSource.getUser()

    fun login(email: String, password: String) = fireBaseSource.login(email, password)

    fun registrer(email: String, password: String) = fireBaseSource.registrer(email, password)

    fun forgetPassword(email: String) = fireBaseSource.forgetPassword(email)

    fun signInWithCredential(credential: AuthCredential) =
        fireBaseSource.signInWithCredential(credential)


}