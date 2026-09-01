package com.example.scheduleappointment.detail_staff

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

data class DetailStaffUiState(
    val currentStaff: Staff? = null
)

sealed class ServiceEvents {
    data class ClickOnService(val service: Service) : ServiceEvents()
}


sealed class ServiceEffects {
    data object NavigateToScheduleAppointment : ServiceEffects()
}

@HiltViewModel
class DetailStaffViewModel
@Inject constructor(
    private val appointmentSession: AppointmentSession
) : ViewModel() {

    private val _effects = Channel<ServiceEffects>()
    val effects = _effects.receiveAsFlow()

    private val _state: MutableStateFlow<DetailStaffUiState> =
        MutableStateFlow(DetailStaffUiState())
    val state = _state.onStart {
        val currentStaff = appointmentSession.draft.value.staff
        _state.update {
            it.copy(
                currentStaff = currentStaff
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailStaffUiState()
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