package com.example.scheduleappointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AppointmentSession
import com.example.core.util.dateFormat
import com.example.core.util.getCurrentDateTime
import com.example.core.util.getInitialTime
import com.example.core.util.toStringFormat
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
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


data class ScheduleScreenUiState(
    val selectedService: Service? = null,
    val branchName: String? = null,
    val currentStaff: Staff? = null,
    val dateAppointment: String = getCurrentDateTime().toStringFormat(dateFormat),
    val hourAppointment: String = getInitialTime(),
    val showDateDialog: Boolean = false,
    val showTimeDialog: Boolean = false
)

sealed class ScheduleScreenEvents {
    object OnDateSelected : ScheduleScreenEvents()
    data class OnConfirmDate(val date: String) : ScheduleScreenEvents()
    object OnDismissDate : ScheduleScreenEvents()
    object OnTimeSelected : ScheduleScreenEvents()
    data class OnConfirmTime(val time: String) : ScheduleScreenEvents()
    object OnDismissTime : ScheduleScreenEvents()
    object OnNextButtonClicked : ScheduleScreenEvents()
}

sealed class ScheduleScreenEffects {
    object NavigateToConfirmationScreen : ScheduleScreenEffects()
}

@HiltViewModel
class ScheduleScreenViewmodel @Inject constructor(
    private val appointmentSession: AppointmentSession
) :
    ViewModel() {


    private val _uiState: MutableStateFlow<ScheduleScreenUiState> =
        MutableStateFlow(ScheduleScreenUiState())

    val uiState = _uiState.onStart {
        val draft = appointmentSession.draft.value
        val selectedService = draft.service
        val branchName = draft.branch?.sucursal?.name
        val currentStaff = draft.staff
        _uiState.update {
            it.copy(
                selectedService = selectedService,
                branchName = branchName,
                currentStaff = currentStaff
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScheduleScreenUiState()
    )

    private val _effects = Channel<ScheduleScreenEffects>()

    val effects = _effects.receiveAsFlow()


    fun onEvents(event: ScheduleScreenEvents) {
        when (event) {
            ScheduleScreenEvents.OnDateSelected -> {
                _uiState.update { it.copy(showDateDialog = true) }
            }

            is ScheduleScreenEvents.OnConfirmDate -> {
                _uiState.update { it.copy(showDateDialog = false, dateAppointment = event.date) }
                appointmentSession.selectDate(event.date)
            }

            ScheduleScreenEvents.OnDismissDate -> {
                _uiState.update { it.copy(showDateDialog = false) }
            }

            ScheduleScreenEvents.OnTimeSelected -> {
                _uiState.update { it.copy(showTimeDialog = true) }
            }

            is ScheduleScreenEvents.OnConfirmTime -> {
                _uiState.update { it.copy(showTimeDialog = false, hourAppointment = event.time) }
                appointmentSession.selectTime(event.time)
            }

            ScheduleScreenEvents.OnDismissTime -> {
                _uiState.update { it.copy(showTimeDialog = false) }
            }

            ScheduleScreenEvents.OnNextButtonClicked -> {
                viewModelScope.launch {
                    _effects.send(ScheduleScreenEffects.NavigateToConfirmationScreen)
                }
            }
        }
    }

}