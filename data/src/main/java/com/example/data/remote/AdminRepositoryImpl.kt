package com.example.data.remote

import com.example.di.qualifiers.RootAppointmentsRef
import com.example.domain.AdminAppointmentUiModel
import com.example.domain.AppointmentFirebase
import com.example.domain.AppointmentStatus
import com.example.domain.repository.AdminRepository
import com.example.domain.state.ApiResult
import com.example.domain.toAppointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    @RootAppointmentsRef private val rootAppointmentsReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) :
    AdminRepository {


    override suspend fun getPendingAppointmentsForAdmin(): ApiResult<List<AdminAppointmentUiModel>> {
        return runCatching {
            val snapshot = rootAppointmentsReference.get().await()
            val allAppointments = mutableListOf<AdminAppointmentUiModel>()

            for (userSnapshot in snapshot.children) {
                for (appointmentSnapshot in userSnapshot.children) {
                    appointmentSnapshot.getValue<AppointmentFirebase>()?.toAppointment()
                        ?.let { appointment ->
                            if (appointment.status == AppointmentStatus.CONFIRMED) {
                                val adminAppointmentUiModel = AdminAppointmentUiModel(
                                    id = appointment.id,
                                    clientName = appointment.clientName,
                                    serviceName = appointment.service,
                                    staffName = appointment.employee,
                                    date = appointment.date,
                                    time = appointment.date,
                                    status = appointment.status.toString()
                                )
                                allAppointments.add(adminAppointmentUiModel)
                            }
                        }
                }
            }
            ApiResult.Success(allAppointments.toList())
        }.getOrElse { exception ->
            ApiResult.Error(exception.message ?: "Unknown error fetching appointments")
        }
    }

    override suspend fun getTotalBookings(): ApiResult<String> {
        return runCatching {
            val snapshot = rootAppointmentsReference.get().await()
            val totalBookings = snapshot.children.sumOf { it.childrenCount }
            ApiResult.Success(totalBookings.toString())
        }.getOrElse { exception ->
            ApiResult.Error(exception.message ?: "Unknown error fetching booking")
        }
    }

    override suspend fun getRevenue(): ApiResult<Double> {
        return runCatching {
            ApiResult.Success(0.0)
        }.getOrElse { exception ->
            ApiResult.Error(exception.message ?: "Unknown error fetching revenue")
        }
    }

    override suspend fun updateAppointmentStatus(
        appointmentId: String,
        newStatus: AppointmentStatus
    ): ApiResult<Unit> {
        return runCatching {
            val currentUser = firebaseAuth.currentUser
                ?: return@runCatching ApiResult.Error("User not authenticated")
            rootAppointmentsReference
                .child(currentUser.uid)
                .child(appointmentId)
                .child("status")
                .setValue(newStatus)
                .await()
            ApiResult.Success(Unit)
        }.getOrElse { exception ->
            ApiResult.Error(exception.message ?: "Failed to update appointment status")
        }
    }


}