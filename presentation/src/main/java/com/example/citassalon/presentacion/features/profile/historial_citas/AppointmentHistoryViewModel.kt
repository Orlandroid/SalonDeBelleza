package com.example.citassalon.presentacion.features.profile.historial_citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.perfil.Appointment
import com.example.domain.state.getResult
import com.example.domain.state.isError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


data class AppointmentHistoryUiState(
    val idAppointment: String? = null,
    val showDialog: Boolean = false,
    val appointments: List<Appointment> = emptyList()
)

sealed class AppointmentHistoryViewState {
    object OnLoading : AppointmentHistoryViewState()
    data class OnContent(val content: AppointmentHistoryUiState) : AppointmentHistoryViewState()
    data class OnError(val error: String) : AppointmentHistoryViewState()
}

sealed class AppointmentHistoryEvents {
    data class OnRemove(val idAppointment: String) : AppointmentHistoryEvents()
    object OnAccept : AppointmentHistoryEvents()
    object OnCancel : AppointmentHistoryEvents()
}

@HiltViewModel
class AppointmentHistoryViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val getAppointmentsUse: GetAppointmentsUse,
    private val deleteAppointmentUseCase: DeleteAppointmentUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<AppointmentHistoryViewState> =
        MutableStateFlow(AppointmentHistoryViewState.OnLoading)
    val state = _state.asStateFlow()

    private val currentState: AppointmentHistoryUiState = AppointmentHistoryUiState()

    init {
        getAppointments()
    }

    fun onEvents(event: AppointmentHistoryEvents) {
        when (event) {
            is AppointmentHistoryEvents.OnAccept -> {
                _state.update {
                    AppointmentHistoryViewState.OnContent(
                        content = currentState.copy(showDialog = false)
                    )
                }
                deleteAppointment(currentState.idAppointment.orEmpty())
            }

            AppointmentHistoryEvents.OnCancel -> {
                _state.update {
                    AppointmentHistoryViewState.OnContent(
                        content = currentState.copy(showDialog = false)
                    )
                }
            }

            is AppointmentHistoryEvents.OnRemove -> {
                _state.update {
                    AppointmentHistoryViewState.OnContent(
                        content = currentState.copy(
                            showDialog = false,
                            idAppointment = event.idAppointment
                        )
                    )
                }
            }
        }
    }


    private fun getAppointments() = viewModelScope.launch {
        delay(2.seconds)
        _state.update { AppointmentHistoryViewState.OnLoading }
        if (!networkHelper.isNetworkConnected()) {
            _state.update { AppointmentHistoryViewState.OnError(error = "Network Error") }
            return@launch
        }
        val appointmentsResult = getAppointmentsUse()
        if (appointmentsResult.isError()) {
            _state.update { AppointmentHistoryViewState.OnError(error = "Error") }
            return@launch
        }
        _state.update {
            AppointmentHistoryViewState.OnContent(
                content = currentState.copy(
                    appointments = appointmentsResult.getResult()
                )
            )
        }
    }

    private fun deleteAppointment(idAppointment: String) = viewModelScope.launch {
        val deleteAppointmentResult = deleteAppointmentUseCase(idAppointment)
        if (deleteAppointmentResult.isError()) {
            _state.update { AppointmentHistoryViewState.OnError(error = "Error") }
            return@launch
        }
        //Launch and effect of that the apointment was removed
    }

}