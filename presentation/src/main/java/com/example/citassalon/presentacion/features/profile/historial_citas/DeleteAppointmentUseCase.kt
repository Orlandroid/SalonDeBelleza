package com.example.citassalon.presentacion.features.profile.historial_citas

import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class DeleteAppointmentUseCase @Inject constructor(
    private val databaseReference: DatabaseReference
) {
    suspend operator fun invoke(idAppointment: String): ApiResult<Any> {
        try {
            databaseReference.child(idAppointment).removeValue().await()
            return ApiResult.Success(Any())
        } catch (e: Exception) {
            return ApiResult.Error(error = e.message.orEmpty())
        }
    }
}
