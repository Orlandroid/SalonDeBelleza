package com.example.citassalon.presentacion.features.profile.historial_citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.appointments.AppointmentsRepository
import com.example.domain.perfil.Appointment
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AppointmentHistoryUiState(
    val idAppointment: String? = null,
    val showDialog: Boolean = false,
    val appointments: List<Appointment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class AppointmentHistoryEvents {
    data class OnRemove(val idAppointment: String) : AppointmentHistoryEvents()
    object OnAccept : AppointmentHistoryEvents()
    object OnCancel : AppointmentHistoryEvents()
    data class OnAppointmentClicked(val appointment: String) : AppointmentHistoryEvents()
}

sealed class AppointmentHistoryEffects {
    data class NavigateToDetail(val idAppointment: String) : AppointmentHistoryEffects()
}

@HiltViewModel
class AppointmentHistoryViewModel @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository
) : ViewModel() {

    private var idAppointment: String? = null

    private val _state: MutableStateFlow<AppointmentHistoryUiState> =
        MutableStateFlow(AppointmentHistoryUiState())
    val state = _state.onStart {
        getAppointments()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AppointmentHistoryUiState()
    )

    private val _effects = Channel<AppointmentHistoryEffects>()
    val effects = _effects.receiveAsFlow()


    fun onEvents(event: AppointmentHistoryEvents) {
        when (event) {
            is AppointmentHistoryEvents.OnAccept -> {
                idAppointment?.let {
                    deleteAppointment(it)
                }
                _state.update { state ->
                    state.copy(showDialog = false, idAppointment = null)
                }
                getAppointments()
            }

            AppointmentHistoryEvents.OnCancel -> {
                _state.update { state ->
                    state.copy(showDialog = false)
                }
            }

            is AppointmentHistoryEvents.OnRemove -> {
                idAppointment = event.idAppointment
                _state.update { it.copy(showDialog = true) }
            }

            is AppointmentHistoryEvents.OnAppointmentClicked -> {
                viewModelScope.launch {
                    _effects.send(AppointmentHistoryEffects.NavigateToDetail(idAppointment = event.appointment))
                }
            }
        }
    }


    private fun getAppointments() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val appointmentsResult = appointmentsRepository.getAppointments()
        if (appointmentsResult.isError()) {
            _state.update { state ->
                state.copy(error = appointmentsResult.getErrorMessage())
            }
            return@launch
        }
        val appointments = appointmentsResult.getContent()
        _state.update { state ->
            state.copy(appointments = appointments, isLoading = false)
        }
    }

    private fun deleteAppointment(idAppointment: String) = viewModelScope.launch {
        val deleteAppointmentResult = appointmentsRepository.deleteAppointment(idAppointment)
        if (deleteAppointmentResult.isError()) {
            _state.update { state ->
                state.copy(error = deleteAppointmentResult.getErrorMessage())
            }
            return@launch
        }
    }

}