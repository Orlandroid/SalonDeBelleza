package com.example.scheduleappointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.dateFormat
import com.example.core.util.getCurrentDateTime
import com.example.core.util.getInitialTime
import com.example.core.util.toStringFormat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ScheduleScreenUiState(
    val dateAppointment: String = getCurrentDateTime().toStringFormat(dateFormat),
    val hourAppointment: String = getInitialTime(),
    val showDateDialog: Boolean = false,
    val showTimeDialog: Boolean = false
)

sealed class ScheduleScreenEvents {
    object OnDateClicked : ScheduleScreenEvents()
    data class OnConfirmDate(val date: String) : ScheduleScreenEvents()
    object OnDismissDate : ScheduleScreenEvents()
    object OnTimeClicked : ScheduleScreenEvents()
    data class OnConfirmTime(val time: String) : ScheduleScreenEvents()
    object OnDismissTime : ScheduleScreenEvents()
    object OnNextButtonClicked : ScheduleScreenEvents()
}

sealed class ScheduleScreenEffects {
    object NavigateToConfirmationScreen : ScheduleScreenEffects()
}

class ScheduleScreenViewmodel : ViewModel() {


    private val _uiState: MutableStateFlow<ScheduleScreenUiState> =
        MutableStateFlow(ScheduleScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<ScheduleScreenEffects>()

    val effects = _effects.receiveAsFlow()


    fun onEvents(event: ScheduleScreenEvents) {
        when (event) {
            ScheduleScreenEvents.OnDateClicked -> {
                _uiState.update { it.copy(showDateDialog = true) }
            }

            is ScheduleScreenEvents.OnConfirmDate -> {
                _uiState.update { it.copy(showDateDialog = false, dateAppointment = event.date) }
            }

            ScheduleScreenEvents.OnDismissDate -> {
                _uiState.update { it.copy(showDateDialog = false) }
            }

            ScheduleScreenEvents.OnTimeClicked -> {
                _uiState.update { it.copy(showTimeDialog = true) }
            }

            is ScheduleScreenEvents.OnConfirmTime -> {
                _uiState.update { it.copy(showTimeDialog = false, hourAppointment = event.time) }
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