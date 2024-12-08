package com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.perfil.AppointmentFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AgendarConfirmacionViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {

    companion object {
        private const val APPOINTMENT_REFERENCE = "Appointment"
    }


    private fun provideFirebaseRealtimeDatabaseReference(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_REFERENCE)
            .child(uuidUser!!)
    }

    fun saveAppointment(appointment: AppointmentFirebase) {
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.child(appointment.idAppointment).setValue(appointment)
            .addOnSuccessListener {
                Log.w("SAVE", "SAVE")
            }.addOnFailureListener {
                Log.w("ERROR", "ERROR AL GUARDAR")
            }
    }

}