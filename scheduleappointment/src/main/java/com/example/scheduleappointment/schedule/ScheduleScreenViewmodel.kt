package com.example.scheduleappointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AppointmentSession
import com.example.core.util.dateFormat
import com.example.core.util.getCurrentDateTime
import com.example.core.util.toStringFormat
import com.example.domain.AvailabilitySlot
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.state.ApiResult
import com.example.domain.use_cases.GetAvailableSlotsUseCase
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
    val hourAppointment: String = "",
    val showDateDialog: Boolean = false,
    val availableSlots: List<AvailabilitySlot> = emptyList(),
    val isLoadingSlots: Boolean = false
)

sealed class ScheduleScreenEvents {
    object OnDateSelected : ScheduleScreenEvents()
    data class OnConfirmDate(val date: String) : ScheduleScreenEvents()
    object OnDismissDate : ScheduleScreenEvents()
    data class OnConfirmTime(val time: String) : ScheduleScreenEvents()
    object OnNextButtonClicked : ScheduleScreenEvents()
}

sealed class ScheduleScreenEffects {
    object NavigateToConfirmationScreen : ScheduleScreenEffects()
}

@HiltViewModel
class ScheduleScreenViewmodel @Inject constructor(
    private val getAvailableSlotsUseCase: GetAvailableSlotsUseCase,
    private val appointmentSession: AppointmentSession,
    private val appointmentsRepository: AppointmentsRepository
) :
    ViewModel() {


    private val _uiState: MutableStateFlow<ScheduleScreenUiState> =
        MutableStateFlow(ScheduleScreenUiState())

    val uiState = _uiState.onStart {
        val draft = appointmentSession.draft.value
        _uiState.update {
            it.copy(
                selectedService = draft.service,
                branchName = draft.branch?.sucursal?.name,
                currentStaff = draft.staff
            )
        }
        fetchAvailableSlots()
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
                fetchAvailableSlots() // Recalculate for the new date
            }

            ScheduleScreenEvents.OnDismissDate -> {
                _uiState.update { it.copy(showDateDialog = false) }
            }

            is ScheduleScreenEvents.OnConfirmTime -> {
                _uiState.update { it.copy(hourAppointment = event.time) }
                appointmentSession.selectTime(event.time)
            }

            ScheduleScreenEvents.OnNextButtonClicked -> {
                viewModelScope.launch {
                    _effects.send(ScheduleScreenEffects.NavigateToConfirmationScreen)
                }
            }
        }
    }

    private fun fetchAvailableSlots() = viewModelScope.launch {
        val draft = appointmentSession.draft.value
        val schedule = draft.branch?.sucursal?.schedule ?: return@launch
        val branchName = draft.branch?.sucursal?.name ?: return@launch
        val staffName = draft.staff?.name ?: return@launch
        val date = _uiState.value.dateAppointment

        _uiState.update { it.copy(isLoadingSlots = true) }

        // 1. Get already booked slots for this specific Date/Staff
        val bookedResult = appointmentsRepository.getBookedSlots(branchName, date, staffName)
        val bookedTimes = if (bookedResult is ApiResult.Success) bookedResult.result else emptyList()

        // 2. Generate slots and mark the booked ones as unavailable
        val slots = getAvailableSlotsUseCase(schedule, bookedTimes)
        
        _uiState.update { 
            it.copy(
                availableSlots = slots,
                isLoadingSlots = false,
                // Reset selected time if it's now unavailable
                hourAppointment = if (bookedTimes.contains(it.hourAppointment)) "" else it.hourAppointment
            ) 
        }
    }

}
