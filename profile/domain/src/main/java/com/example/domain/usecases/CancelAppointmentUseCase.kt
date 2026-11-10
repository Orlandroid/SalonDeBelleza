package com.example.domain.usecases

import com.example.domain.repository.AppointmentsRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.isError
import com.example.model.state.models.AppointmentStatus
import javax.inject.Inject

class CancelAppointmentUseCase @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository
) {
    suspend operator fun invoke(appointmentId: String): ApiResult<Unit> {
        val result = appointmentsRepository.getAppointmentById(appointmentId)
        if (result.isError()) {
            return ApiResult.Error(result.getErrorMessage() ?: "Appointment not found")
        }

        val appointment = result.getContent()
        if (appointment.status == AppointmentStatus.CANCELLED) {
            return ApiResult.Error("Appointment is already cancelled")
        }

        val cancelledAppointment = appointment.copy(status = AppointmentStatus.CANCELLED)
        val updateResult = appointmentsRepository.updateAppointment(
            appointmentId = appointmentId,
            appointment = cancelledAppointment
        )

        return if (updateResult.isError()) {
            ApiResult.Error(updateResult.getErrorMessage() ?: "Error cancelling appointment")
        } else {
            ApiResult.Success(Unit)
        }
    }
}
