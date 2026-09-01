package com.example.scheduleappointment.schedule_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.entities.remote.migration.Service
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.state.isSuccess
import com.example.domain.transaction.TransactionType
import com.example.domain.use_cases.PurchaseProductsUseCase
import com.example.scheduleappointment.mainflow.AppointmentSession
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
    val service: Service? = null,
    val date: String? = null,
    val time: String? = null,
    val showConfirmationDialog: Boolean = false,
    val showAnimation: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ConfirmScheduleViewModel @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val purchaseProductsUseCase: PurchaseProductsUseCase,
    private val appointmentSession: AppointmentSession
) : ViewModel() {

    private companion object {
        const val MXN_TO_USD_CONVERSION_FACTOR = 3
    }


    private val _uiState: MutableStateFlow<ScheduleAppointmentState> =
        MutableStateFlow(ScheduleAppointmentState())
    val uiState = _uiState.onStart {
        val draft = appointmentSession.draft.value
        _uiState.update {
            it.copy(
                branchName = draft.branch?.sucursal?.name,
                staffName = draft.staff?.name,
                service = draft.service,
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
                _uiState.update { it.copy(showConfirmationDialog = false) }
                saveAppointment()
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


    private fun saveAppointment() {
        val draft = appointmentSession.draft.value
        val appointment = AppointmentFirebase(
            establishment = draft.branch?.sucursal?.name.orEmpty(),
            service = draft.service?.name.orEmpty(),
            date = draft.date,
            hour = draft.time,
            total = draft.service?.precio.toString()
        )
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val saveAppointmentResult = appointmentsRepository.saveAppointment(appointment)
            if (saveAppointmentResult.isSuccess()) {
                _uiState.update { it.copy(showAnimation = true) }
                _effects.send(ScheduleAppointmentEffects.NavigateToAppointComplete)
                purchaseProductsUseCase.invoke(
                    amount = appointment.total.toLong() / MXN_TO_USD_CONVERSION_FACTOR,
                    transactionType = TransactionType.SERVICE_PAYMENT,
                    description = "${appointment.service} at ${appointment.establishment}"
                )
            } else {
                //Todo add some kind of screen the creation of the appointment failed
            }
        }
    }


}