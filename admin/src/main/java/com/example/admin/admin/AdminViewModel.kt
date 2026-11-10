package com.example.admin.admin

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.AdminAppointmentUiModel
import com.example.domain.AdminMetrics
import com.example.domain.repository.AdminRepository
import com.example.domain.usecases.GetAdminDashboardDataUseCase
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.isSuccess
import com.example.model.state.models.AppointmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class AdminUiState(
    val isLoading: Boolean = false,
    val metrics: AdminMetrics = AdminMetrics(),
    val pendingAppointments: List<AdminAppointmentUiModel> = emptyList(),
    val errorMessage: String? = null
)

sealed class AdminEvents {
    data class OnApprove(
        val appointmentId: String
    ) : AdminEvents()

    data class OnReject(
        val appointmentId: String
    ) : AdminEvents()

    data object OnRefresh : AdminEvents()

    data object OnClearError : AdminEvents()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: AdminRepository,
    private val getAdminDashboardDataUseCase: GetAdminDashboardDataUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> =
        _uiState
            .onStart {
                getDataForDashboard()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AdminUiState()
            )

    fun onEvents(event: AdminEvents) {
        when (event) {
            is AdminEvents.OnApprove -> {
                approveAppointment(event.appointmentId)
            }

            is AdminEvents.OnReject -> {
                rejectAppointment(event.appointmentId)
            }

            AdminEvents.OnRefresh -> {
                viewModelScope.launch {
                    getDataForDashboard()
                }
            }

            AdminEvents.OnClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }

    suspend fun getDataForDashboard() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        val result = getAdminDashboardDataUseCase()
        if (result.isSuccess()) {
            val data = result.getContent()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    metrics = data.metrics,
                    pendingAppointments = data.pendingAppointments
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = result.getErrorMessage()
                )
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
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.updateAppointmentStatus(
                appointmentId = appointmentId,
                newStatus = newStatus
            )
            if (result.isSuccess()) {
                getDataForDashboard()
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.getErrorMessage() ?: "Failed to update appointment status"
                    )
                }
            }
        }
    }
}
