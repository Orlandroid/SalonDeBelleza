package com.example.scheduleappointment.detail_staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AppointmentSession
import com.example.domain.entities.StaffRatingSummary
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.GetStaffRatingUseCase
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
    val currentStaff: Staff? = null,
    val ratingSummary: StaffRatingSummary = StaffRatingSummary(),
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
    private val appointmentSession: AppointmentSession,
    private val getStaffRatingUseCase: GetStaffRatingUseCase
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
        getStaffRatting()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailStaffUiState()
    )

    private fun getStaffRatting() {
        viewModelScope.launch {
            val draft = appointmentSession.draft.value
            val branchId = draft.branch?.sucursal?.id ?: ""
            val staffId = draft.staff?.id ?: ""
            val compositeId = "${branchId}_${staffId}"
            val result = getStaffRatingUseCase(compositeId)
            if (result.isSuccess()) {
                _state.update { it.copy(ratingSummary = result.getContent()) }
            }
        }
    }


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