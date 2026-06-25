package com.example.citassalon.presentacion.features.profile.historial_detail

import com.example.domain.entities.local.AppointmentObject
import com.example.domain.mappers.toAppointmentObject
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.resume

class GetAppointmentUseCase @Inject constructor(
    @param:Named("Appointment")
    private val databaseReference: DatabaseReference
) {

    suspend operator fun invoke(appointmentId: String): ApiResult<AppointmentObject> =
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
}