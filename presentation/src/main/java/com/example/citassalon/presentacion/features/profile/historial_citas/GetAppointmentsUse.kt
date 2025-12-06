package com.example.citassalon.presentacion.features.profile.historial_citas

import com.example.domain.perfil.Appointment
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.perfil.toAppointment
import com.example.domain.state.ApiResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject


class GetAppointmentsUse @Inject constructor(
    private val databaseReference: DatabaseReference
) {

    operator fun invoke(): ApiResult<List<Appointment>> {
        val listOfAppointments = arrayListOf<Appointment>()
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val appointment = it.getValue<AppointmentFirebase>()
                        appointment?.let {
                            listOfAppointments.add(appointment.toAppointment())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    AppointmentHistoryViewState.OnError(error = error.message)
                }
            }
        )
        return ApiResult.Success(listOfAppointments)
    }
}