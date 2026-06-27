package com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.IoDispatcher
import com.example.data.remote.appointments.AppointmentsRepository
import com.example.domain.perfil.AppointmentFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ScheduleAppointmentEffects {
    object NavigateToAppointComplete : ScheduleAppointmentEffects()
}

sealed class ScheduleAppointmentEvents {
    data class OnConfirmationAppointmentAccepted(val appointment: AppointmentFirebase) :
        ScheduleAppointmentEvents()

    object OnConfirmationDialogCancel : ScheduleAppointmentEvents()
    object OnSaveAppointment : ScheduleAppointmentEvents()
}

data class ScheduleAppointmentState(
    val showConfirmationDialog: Boolean = false,
    val showAnimation: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ConfirmScheduleViewModel @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState: MutableStateFlow<ScheduleAppointmentState> =
        MutableStateFlow(ScheduleAppointmentState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<ScheduleAppointmentEffects>()
    val effects = _effects.receiveAsFlow()

    fun onEvents(event: ScheduleAppointmentEvents) {
        when (event) {
            is ScheduleAppointmentEvents.OnConfirmationAppointmentAccepted -> {
                _uiState.update { it.copy(showConfirmationDialog = false) }
                saveAppointment(event.appointment)
            }

            ScheduleAppointmentEvents.OnConfirmationDialogCancel -> {
                _uiState.update { it.copy(showConfirmationDialog = false) }
            }

            ScheduleAppointmentEvents.OnSaveAppointment -> {
                _uiState.update { it.copy(showConfirmationDialog = true) }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.update { it.copy(error = exception.message) }
    }


    private fun saveAppointment(appointment: AppointmentFirebase) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            appointmentsRepository.saveAppointment(appointment)
            _uiState.update { it.copy(showAnimation = true) }
            _effects.send(ScheduleAppointmentEffects.NavigateToAppointComplete)
        }
    }


}