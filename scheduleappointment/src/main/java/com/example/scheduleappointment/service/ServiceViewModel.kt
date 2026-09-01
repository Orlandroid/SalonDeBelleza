package com.example.scheduleappointment.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.scheduleappointment.mainflow.AppointmentSession
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


data class ServiceScreenUiState(
    val branchName: String? = null,
    val currentStaff: Staff? = null,
    val services: List<Service> = emptyList()
)

sealed class ServiceEvents {
    data class ClickOnService(val service: Service) : ServiceEvents()
}


sealed class ServiceEffects {
    data object NavigateToScheduleAppointment : ServiceEffects()
}

@HiltViewModel
class ServiceViewModel
@Inject constructor(
    private val appointmentSession: AppointmentSession
) : ViewModel() {

    private val _effects = Channel<ServiceEffects>()
    val effects = _effects.receiveAsFlow()

    private val _state: MutableStateFlow<ServiceScreenUiState> =
        MutableStateFlow(ServiceScreenUiState())
    val state = _state.onStart {
        val branch = appointmentSession.draft.value.branch
        val branchName = branch?.sucursal?.name
        val currentStaff = appointmentSession.draft.value.staff
        _state.update {
            it.copy(
                branchName = branchName,
                currentStaff = currentStaff,
                services = branch?.services ?: emptyList()
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ServiceScreenUiState()
    )


    fun onEvents(event: ServiceEvents) {
        when (event) {
            is ServiceEvents.ClickOnService -> {
                appointmentSession.selectService(event.service)
                sentEffect(ServiceEffects.NavigateToScheduleAppointment)
            }
        }
    }

    private fun sentEffect(effects: ServiceEffects) {
        viewModelScope.launch {
            _effects.send(effects)
        }
    }


}