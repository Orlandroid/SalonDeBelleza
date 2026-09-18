package com.example.domain.use_cases

import com.example.domain.AppointmentFirebase
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isSuccess
import com.example.domain.transaction.TransactionType
import javax.inject.Inject


class SaveAppointmentUseCase @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository,
    private val purchaseProductsUseCase: PurchaseProductsUseCase
) {

    private companion object {
        const val MXN_TO_USD_CONVERSION_FACTOR = 3
    }

    suspend operator fun invoke(
        establishment: String,
        employeeName: String,
        employeeId: String,
        service: String,
        date: String,
        hour: String,
        total: String,
    ): ApiResult<Unit> {
        val appointment = AppointmentFirebase(
            establishment = establishment,
            service = service,
            date = date,
            hour = hour,
            total = total,
            employee = employeeName,
            employeeId = employeeId
        )
        val saveAppointmentResult = appointmentsRepository.saveAppointment(
            appointment
        )
        if (saveAppointmentResult.isSuccess()) {

            appointmentsRepository.bookMasterSchedule(
                branchName = establishment,
                date = date,
                staffName = employeeName,
                time = hour,
                appointmentId = appointment.idAppointment
            )

            val purchaseResult = purchaseProductsUseCase.invoke(
                amount = total.toDouble().toLong() / MXN_TO_USD_CONVERSION_FACTOR,
                transactionType = TransactionType.SERVICE_PAYMENT,
                description = "${appointment.service} at ${appointment.establishment}"
            )
            return if (purchaseResult.isSuccess()) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error(purchaseResult.getErrorMessage() ?: "Error processing payment")
            }
        } else {
            return ApiResult.Error(
                saveAppointmentResult.getErrorMessage() ?: "Error saving appointment"
            )
        }
    }

}
