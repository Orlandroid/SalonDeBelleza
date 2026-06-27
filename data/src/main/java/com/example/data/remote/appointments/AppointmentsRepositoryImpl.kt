package com.example.data.remote.appointments

import com.example.data.di.AppointmentsRef
import com.example.domain.entities.local.AppointmentObject
import com.example.domain.mappers.toAppointmentObject
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
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class AppointmentsRepositoryImpl @Inject constructor(
    @param:AppointmentsRef private val databaseReference: DatabaseReference
) :
    AppointmentsRepository {


    override suspend fun deleteAppointment(idAppointment: String): ApiResult<Any> {
        try {
            databaseReference.child(idAppointment).removeValue().await()
            return ApiResult.Success(Any())
        } catch (e: Exception) {
            return ApiResult.Error(error = e.message.orEmpty())
        }
    }


    override suspend fun getAppointments(): ApiResult<List<Appointment>> =
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


    override suspend fun getSingleAppointment(appointmentId: String): ApiResult<AppointmentObject> =
        suspendCancellableCoroutine { continuation ->
            databaseReference.child(appointmentId).get().addOnSuccessListener { snapshot ->
                val appointment = snapshot.getValue<AppointmentFirebase>()?.toAppointmentObject()
                if (appointment != null) {
                    continuation.resume(ApiResult.Success(appointment))
                } else {
                    continuation.resume(ApiResult.Error("Appointment not found"))
                }
            }.addOnFailureListener { exception ->
                continuation.resume(ApiResult.Error(exception.message ?: "Unknown error"))
            }
        }


    override suspend fun saveAppointment(appointment: AppointmentFirebase): ApiResult<Any> {
        runCatching {
            databaseReference.child(appointment.idAppointment).setValue(appointment).await()
            return ApiResult.Success(Any())
        }.getOrElse {
            return ApiResult.Error(it.message)
        }
    }

}