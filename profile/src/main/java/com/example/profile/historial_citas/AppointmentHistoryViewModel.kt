package com.example.profile.historial_citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Appointment
import com.example.domain.AppointmentStatus
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import com.example.domain.use_cases.loyalty.CompleteAppointmentUseCase
import com.example.profile.R
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
    data class OnComplete(val idAppointment: String) : AppointmentHistoryEvents()
    data class OnCancelAppointment(val idAppointment: String) : AppointmentHistoryEvents()
}

sealed class AppointmentHistoryEffects {
    data class NavigateToDetail(val idAppointment: String) : AppointmentHistoryEffects()
}

@HiltViewModel
class AppointmentHistoryViewModel @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository,
    private val completeAppointmentUseCase: CompleteAppointmentUseCase
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

            is AppointmentHistoryEvents.OnComplete -> {
                completeAppointment(event.idAppointment)
            }

            is AppointmentHistoryEvents.OnCancelAppointment -> {
                cancelAppointment(event.idAppointment)
            }
        }
    }

    fun getRandomNoDataAnimation(): Int = when ((1..3).random()) {
        1 -> R.raw.no_data_animation
        2 -> R.raw.no_data_available
        else -> R.raw.no_data_found
    }


    private fun getAppointments() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val appointmentsResult = appointmentsRepository.getAppointments()
        if (appointmentsResult.isError()) {
            _state.update { state ->
                state.copy(error = appointmentsResult.getErrorMessage(), isLoading = false)
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
        getAppointments()
    }

    private fun completeAppointment(idAppointment: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = completeAppointmentUseCase(idAppointment)
        if (result.isError()) {
            _state.update { it.copy(error = result.getErrorMessage(), isLoading = false) }
        } else {
            getAppointments()
        }
    }

    private fun cancelAppointment(idAppointment: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = appointmentsRepository.getAppointmentById(idAppointment)
        if (result.isError()) {
            _state.update { it.copy(error = result.getErrorMessage(), isLoading = false) }
            return@launch
        }
        val appointment = result.getContent()
        val cancelledAppointment = appointment.copy(status = AppointmentStatus.CANCELLED)
        val updateResult = appointmentsRepository.updateAppointment(idAppointment, cancelledAppointment)
        if (updateResult.isError()) {
            _state.update { it.copy(error = updateResult.getErrorMessage(), isLoading = false) }
        } else {
            getAppointments()
        }
    }

}
