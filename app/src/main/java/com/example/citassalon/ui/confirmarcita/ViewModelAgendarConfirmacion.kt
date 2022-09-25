package com.example.citassalon.ui.confirmarcita

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.citassalon.util.DATABASE_NAME
import com.example.citassalon.data.models.remote.Appointment as RemoteAppointment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelAgendarConfirmacion @Inject constructor(
    private val databaseReference: DatabaseReference
) :
    ViewModel() {


    fun saveAppointMent(appointment: RemoteAppointment) {
        databaseReference.child(UUID.randomUUID().toString()).setValue(appointment)
            .addOnSuccessListener {
                Log.w("SAVE", "SAVE")
            }.addOnFailureListener {
                Log.w("ERROR", "ERROR AL GUARDAR")
            }
    }

    fun getAppointments() {
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