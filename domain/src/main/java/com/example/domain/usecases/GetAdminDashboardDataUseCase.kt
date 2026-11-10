package com.example.domain.usecases

import com.example.domain.AdminAppointmentUiModel
import com.example.domain.AdminMetrics
import com.example.domain.repository.AdminRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.isError
import com.example.model.state.isSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class AdminDashboardData(
    val metrics: AdminMetrics,
    val pendingAppointments: List<AdminAppointmentUiModel>
)

class GetAdminDashboardDataUseCase @Inject constructor(
    private val adminRepository: AdminRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): ApiResult<AdminDashboardData> = withContext(ioDispatcher) {
        val revenueDeferred = async { adminRepository.getRevenue() }
        val bookingsDeferred = async { adminRepository.getTotalBookings() }
        val pendingDeferred = async { adminRepository.getPendingAppointmentsForAdmin() }

        val revenueResult = revenueDeferred.await()
        val bookingsResult = bookingsDeferred.await()
        val pendingResult = pendingDeferred.await()

        val hasError = revenueResult.isError() || bookingsResult.isError() || pendingResult.isError()
        if (hasError) {
            val errorMsg = revenueResult.getErrorMessage()
                ?: bookingsResult.getErrorMessage()
                ?: pendingResult.getErrorMessage()
                ?: "Error loading dashboard data"
            return@withContext ApiResult.Error(errorMsg)
        }

        val revenue = if (revenueResult.isSuccess()) revenueResult.getContent() else 0.0
        val bookingsStr = if (bookingsResult.isSuccess()) bookingsResult.getContent() else "0"
        val totalBookings = bookingsStr.toIntOrNull() ?: 0
        val pendingAppointments = if (pendingResult.isSuccess()) pendingResult.getContent() else emptyList()

        val dashboardData = AdminDashboardData(
            metrics = AdminMetrics(
                totalRevenue = revenue,
                totalBookings = totalBookings,
                pendingAppointmentsCount = pendingAppointments.size
            ),
            pendingAppointments = pendingAppointments
        )

        ApiResult.Success(dashboardData)
    }
}
