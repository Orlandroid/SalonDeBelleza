package com.example.admin.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.AdminAppointmentUiModel
import com.example.domain.AdminMetrics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminUiState(
    val isLoading: Boolean = false,
    val metrics: AdminMetrics = AdminMetrics(),
    val pendingAppointments: List<AdminAppointmentUiModel> = emptyList(),
    val errorMessage: String? = null
)

class AdminViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        loadAdminDashboardData()
    }

    fun loadAdminDashboardData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // TODO: Replace with  Use Cases / Repositories
                val mockMetrics = AdminMetrics(
                    totalBookings = 48,
                    totalRevenue = 1425.00,
                    activeClientsCount = 32,
                    pendingAppointmentsCount = 3
                )

                val mockPending = listOf(
                    AdminAppointmentUiModel(
                        "1",
                        "Maria Garcia",
                        "Haircut & Styling",
                        "Ana Lopez",
                        "2023-11-15",
                        "10:00 AM",
                        "PENDING"
                    ),
                    AdminAppointmentUiModel(
                        "2",
                        "Carlos Ruiz",
                        "Beard Trim",
                        "Juan Perez",
                        "2023-11-15",
                        "11:30 AM",
                        "PENDING"
                    ),
                    AdminAppointmentUiModel(
                        "3",
                        "Lucia Gomez",
                        "Manicure",
                        "Sofia Gomez",
                        "2023-11-16",
                        "02:00 PM",
                        "PENDING"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        metrics = mockMetrics,
                        pendingAppointments = mockPending
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun updateAppointmentStatus(appointmentId: String, newStatus: String) {
        viewModelScope.launch {

            _uiState.update { state ->
                val updatedList = state.pendingAppointments.filterNot { it.id == appointmentId }
                state.copy(
                    pendingAppointments = updatedList,
                    metrics = state.metrics.copy(
                        pendingAppointmentsCount = (state.metrics.pendingAppointmentsCount - 1).coerceAtLeast(
                            0
                        )
                    )
                )
            }
        }
    }
}