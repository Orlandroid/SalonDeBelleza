package com.example.domain.use_cases.loyalty

import com.example.domain.AppointmentStatus
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CompleteAppointmentUseCase @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository,
    private val earnPointsUseCase: EarnPointsUseCase,
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(appointmentId: String): ApiResult<Unit> {
        val appointmentResult = appointmentsRepository.getAppointmentById(appointmentId)
        if (appointmentResult.isError()) return ApiResult.Error()

        val appointment = appointmentResult.getContent()

        if (appointment.status == AppointmentStatus.COMPLETED) {
            return ApiResult.Error("Appointment already completed")
        }

        val updatedAppointment = appointment.copy(status = AppointmentStatus.COMPLETED)
        val updateResult =
            appointmentsRepository.updateAppointment(appointmentId, updatedAppointment)
        if (updateResult.isError()) return ApiResult.Error()

        val userId = firebaseAuth.uid ?: return ApiResult.Error("User not authenticated")
        val points = appointment.total.filter { it.isDigit() }.toIntOrNull() ?: 0

        if (points > 0) {
            return earnPointsUseCase(
                userId = userId,
                pointsEarned = points
            )
        }

        return ApiResult.Success(Unit)
    }
}
