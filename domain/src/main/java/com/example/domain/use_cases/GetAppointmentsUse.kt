package com.example.domain.use_cases

import com.example.domain.perfil.Appointment
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.perfil.toAppointment
import com.example.domain.state.ApiResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.resume

class GetAppointmentsUseCase @Inject constructor(
    @param:Named("Appointment")
    private val databaseReference: DatabaseReference
) {

    suspend operator fun invoke(): ApiResult<List<Appointment>> =
        suspendCancellableCoroutine { continuation ->
            databaseReference.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val appointments = snapshot.children.mapNotNull { child ->
                            child.getValue<AppointmentFirebase>()?.toAppointment()
                        }
                        continuation.resume(ApiResult.Success(appointments))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(ApiResult.Error(error.toException().toString()))
                    }
                }
            )
        }
}