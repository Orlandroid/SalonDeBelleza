package com.example.scheduleappointment.schedule_staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

data class ScheduleStaffUiState(
    val branchName: String? = null,
    val staffs: List<Staff> = emptyList(),
)

sealed class ScheduleStaffEvents {
    data object OnRandomStaff : ScheduleStaffEvents()
    data class ClickOnImageStaff(val staff: Staff) : ScheduleStaffEvents()
    data class ClickOnStaff(val staff: Staff) : ScheduleStaffEvents()
}


sealed class ScheduleStaffEffects {
    data object GoToDetailStaffScreen : ScheduleStaffEffects()
    data object GoToScheduleService : ScheduleStaffEffects()
}

@HiltViewModel
class ScheduleStaffViewmodel
@Inject constructor(
    private val appointmentSession: AppointmentSession
) : ViewModel() {

    private val _branchSideEffects = Channel<ScheduleStaffEffects>()
    val branchSideEffects = _branchSideEffects.receiveAsFlow()

    private val _state: MutableStateFlow<ScheduleStaffUiState> =
        MutableStateFlow(ScheduleStaffUiState())
    val state = _state.onStart {
        val branch = appointmentSession.draft.value.branch
        val branchName = branch?.sucursal?.name
        val staffs = branch?.staffs ?: emptyList()
        _state.update { it.copy(branchName = branchName, staffs = staffs) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScheduleStaffUiState()
    )


    fun onEvents(event: ScheduleStaffEvents) {
        when (event) {
            ScheduleStaffEvents.OnRandomStaff -> {
                val randomIndex = _state.value.staffs.indices.random()
                val randomStaff = _state.value.staffs[randomIndex]
                appointmentSession.selectStaff(randomStaff)
                sentEffect(ScheduleStaffEffects.GoToScheduleService)
            }

            is ScheduleStaffEvents.ClickOnImageStaff -> {
                appointmentSession.selectStaff(event.staff)
                sentEffect(ScheduleStaffEffects.GoToDetailStaffScreen)
            }

            is ScheduleStaffEvents.ClickOnStaff -> {
                appointmentSession.selectStaff(event.staff)
                sentEffect(ScheduleStaffEffects.GoToScheduleService)
            }
        }
    }

    private fun sentEffect(effects: ScheduleStaffEffects) {
        viewModelScope.launch {
            _branchSideEffects.send(effects)
        }
    }


}