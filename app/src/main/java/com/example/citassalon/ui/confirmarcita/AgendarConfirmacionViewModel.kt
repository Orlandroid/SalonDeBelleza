package com.example.citassalon.ui.confirmarcita

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.citassalon.main.NetworkHelper
import com.google.firebase.auth.FirebaseAuth
import com.example.citassalon.data.models.remote.Appointment as RemoteAppointment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AgendarConfirmacionViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
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

    fun saveAppointMent(appointment: RemoteAppointment) {
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.child(appointment.idAppointment).setValue(appointment)
            .addOnSuccessListener {
                Log.w("SAVE", "SAVE")
            }.addOnFailureListener {
                Log.w("ERROR", "ERROR AL GUARDAR")
            }
    }

    fun getAppointments() {
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val appointment = it.getValue<RemoteAppointment>()
                    Log.w("POST", appointment.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("ERROR", error.message)
            }
        })
    }


}