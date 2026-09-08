package com.example.scheduleappointment.schedule_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AppointmentSession
import com.example.di.IoDispatcher
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.SaveAppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ScheduleAppointmentEffects {
    object NavigateToAppointComplete : ScheduleAppointmentEffects()
}

sealed class ScheduleAppointmentEvents {
    object OnConfirmationAppointmentAccepted : ScheduleAppointmentEvents()
    object OnConfirmationDialogCancel : ScheduleAppointmentEvents()
    object OnSaveAppointment : ScheduleAppointmentEvents()
}

data class ScheduleAppointmentState(
    val branchName: String? = null,
    val staffName: String? = null,
    val servicePrice: String = "0",
    val serviceName: String = "",
    val date: String = "",
    val time: String = "",
    val showConfirmationDialog: Boolean = false,
    val showAnimation: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ConfirmScheduleViewModel @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val saveAppointmentUseCase: SaveAppointmentUseCase,
    private val appointmentSession: AppointmentSession
) : ViewModel() {


    private val _uiState: MutableStateFlow<ScheduleAppointmentState> =
        MutableStateFlow(ScheduleAppointmentState())
    val uiState = _uiState.onStart {
        val draft = appointmentSession.draft.value
        _uiState.update {
            it.copy(
                branchName = draft.branch?.sucursal?.name,
                staffName = draft.staff?.name,
                servicePrice = draft.service?.precio.toString(),
                serviceName = draft.service?.name.toString(),
                date = draft.date,
                time = draft.time
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScheduleAppointmentState()
    )

    private val _effects = Channel<ScheduleAppointmentEffects>()
    val effects = _effects.receiveAsFlow()

    fun onEvents(event: ScheduleAppointmentEvents) {
        when (event) {
            is ScheduleAppointmentEvents.OnConfirmationAppointmentAccepted -> {
                viewModelScope.launch(coroutineExceptionHandler + ioDispatcher) {
                    _uiState.update { it.copy(showConfirmationDialog = false) }
                    saveAppointment()
                }
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


    private suspend fun saveAppointment() {
        val draft = appointmentSession.draft.value
        val saveAppointmentResult = saveAppointmentUseCase.invoke(
            establishment = draft.branch?.sucursal?.name.orEmpty(),
            employee = draft.staff?.name.orEmpty(),
            service = draft.service?.name.orEmpty(),
            date = draft.date,
            hour = draft.time,
            total = draft.service?.precio.toString()
        )
        if (saveAppointmentResult.isSuccess()) {
            _uiState.update { it.copy(showAnimation = true) }
            _effects.send(ScheduleAppointmentEffects.NavigateToAppointComplete)
        } else {
            //Todo add some kind of screen the creation of the appointment failed
        }
    }


}