package com.example.citassalon.presentacion.features.schedule_appointment.mainflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.extensions.dateFormat
import com.example.citassalon.presentacion.features.extensions.getCurrentDateTime
import com.example.citassalon.presentacion.features.extensions.getInitialTime
import com.example.citassalon.presentacion.features.extensions.toStringFormat
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchFlow
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AppointmentFlowUiState(
    val listOfStaffs: List<Staff> = emptyList(),
    val branchName: String = "",
    val currentStaff: Staff? = null,
    val listOfServices: List<Service> = emptyList(),
    val dateAppointment: String = getCurrentDateTime().toStringFormat(dateFormat),
    val timeAppointment: String = getInitialTime()
)

sealed class ScheduleAppointmentEvents {
    data class ClickOnBranch(val branch: NegoInfo) : ScheduleAppointmentEvents()
    data class ClickOnStaff(val staff: Staff) : ScheduleAppointmentEvents()
    data object OnRandomStaff : ScheduleAppointmentEvents()
    data class ClickOnImageStaff(val staff: Staff) : ScheduleAppointmentEvents()
    data class ClickOnService(val service: Service) : ScheduleAppointmentEvents()
    data class TimeSelected(val time: String) : ScheduleAppointmentEvents()
    data class DateSelected(val date: String) : ScheduleAppointmentEvents()
}

sealed class ScheduleAppointmentsSideEffects {
    data object GotoBranchInfo : ScheduleAppointmentsSideEffects()
    data object GoToScheduleStaff : ScheduleAppointmentsSideEffects()
    data object GoToDetailStaffScreen : ScheduleAppointmentsSideEffects()
    data object GoToScheduleService : ScheduleAppointmentsSideEffects()
}

class AppointmentFlowViewModel : ViewModel() {

    var currentFlowBranch: BranchFlow? = null

    private val _branchSideEffects = Channel<ScheduleAppointmentsSideEffects>()
    val branchSideEffects = _branchSideEffects.receiveAsFlow()

    private val _staffUiState: MutableStateFlow<AppointmentFlowUiState> =
        MutableStateFlow(AppointmentFlowUiState())
    val staffUiState = _staffUiState.asStateFlow()


    fun onEvents(event: ScheduleAppointmentEvents) {
        when (event) {
            is ScheduleAppointmentEvents.ClickOnBranch -> {
                clickOnBranch(event.branch)
            }


            is ScheduleAppointmentEvents.ClickOnStaff -> {
                _staffUiState.update { oldState -> oldState.copy(currentStaff = event.staff) }
                sentEvent(ScheduleAppointmentsSideEffects.GoToScheduleService)
            }


            is ScheduleAppointmentEvents.OnRandomStaff -> {
                val randomStaff = staffUiState.value.listOfStaffs.indices.random()
                selectStaffAndContinue(staffUiState.value.listOfStaffs[randomStaff])
            }

            is ScheduleAppointmentEvents.ClickOnImageStaff -> {
                selectStaffAndContinue(event.staff)
            }

            is ScheduleAppointmentEvents.ClickOnService -> {
                //todo make Nabigation to service screen
            }

            is ScheduleAppointmentEvents.DateSelected -> {
                _staffUiState.update { oldState -> oldState.copy(dateAppointment = event.date) }
            }

            is ScheduleAppointmentEvents.TimeSelected -> {
                _staffUiState.update { oldState -> oldState.copy(timeAppointment = event.time) }
            }
        }
    }


    private fun selectStaffAndContinue(staff: Staff) {
        _staffUiState.update { oldState ->
            oldState.copy(
                currentStaff = staff
            )
        }
        sentEvent(ScheduleAppointmentsSideEffects.GoToScheduleService)
    }

    private fun clickOnBranch(branch: NegoInfo) {
        setCurrentBranch(branch = branch)
        when (currentFlowBranch) {
            BranchFlow.SCHEDULE_APPOINTMENT -> {
                sentEvent(ScheduleAppointmentsSideEffects.GoToScheduleStaff)
            }

            BranchFlow.INFO -> {
                sentEvent(ScheduleAppointmentsSideEffects.GotoBranchInfo)
            }

            null -> {}
        }
    }

    private fun sentEvent(effects: ScheduleAppointmentsSideEffects) {
        viewModelScope.launch(Dispatchers.IO) {
            _branchSideEffects.send(effects)
        }
    }

    private fun AppointmentFlowViewModel.setCurrentBranch(branch: NegoInfo) {
        _staffUiState.update { oldState ->
            oldState.copy(
                listOfStaffs = branch.staffs,
                branchName = branch.sucursal.name,
                listOfServices = branch.services
            )
        }
    }

}

