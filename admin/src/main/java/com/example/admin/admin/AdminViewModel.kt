package com.example.admin.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.AdminAppointmentUiModel
import com.example.domain.AdminMetrics
import com.example.domain.AppointmentStatus
import com.example.domain.repository.AdminRepository
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminUiState(
    val isLoading: Boolean = false,
    val metrics: AdminMetrics = AdminMetrics(),
    val pendingAppointments: List<AdminAppointmentUiModel> = emptyList(),
    val errorMessage: String? = null
)

sealed class AdminEvents {
    data class OnApprove(val appointmentId: String) : AdminEvents()
    data class OnReject(val appointmentId: String) : AdminEvents()
}


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: AdminRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.onStart {
        getDataForDashboard()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AdminUiState()
    )


    suspend fun getDataForDashboard() {
        val revenueResult = repository.getRevenue()
        val bookingsResult = repository.getTotalBookings()
        val pendingAppointmentsResult = repository.getPendingAppointmentsForAdmin()
        val revenue = if (revenueResult.isSuccess()) {
            revenueResult.getContent()
        } else {
            0.0
        }
        val bookings = if (bookingsResult.isSuccess()) {
            bookingsResult.getContent()
        } else {
            "0"
        }
        val pendingAppointments = if (pendingAppointmentsResult.isSuccess()) {
            pendingAppointmentsResult.getContent()
        } else {
            emptyList()
        }
        _uiState.update {
            it.copy(
                metrics = AdminMetrics(
                    totalRevenue = revenue,
                    totalBookings = bookings.toInt(),
                    pendingAppointmentsCount = pendingAppointments.size
                ),
                pendingAppointments = pendingAppointments
            )
        }
    }

    fun onEvents(event: AdminEvents) {
        when (event) {
            is AdminEvents.OnApprove -> {
                approveAppointment(event.appointmentId)
            }

            is AdminEvents.OnReject -> {
                rejectAppointment(event.appointmentId)
            }
        }
    }

    private fun approveAppointment(appointmentId: String) {
        updateAppointmentStatus(
            appointmentId = appointmentId,
            newStatus = AppointmentStatus.COMPLETED
        )
    }

    private fun rejectAppointment(appointmentId: String) {
        updateAppointmentStatus(
            appointmentId = appointmentId,
            newStatus = AppointmentStatus.CANCELLED
        )
    }

    fun updateAppointmentStatus(
        appointmentId: String,
        newStatus: AppointmentStatus
    ) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(
                appointmentId = appointmentId,
                newStatus = newStatus
            )
            getDataForDashboard()
        }
    }


}