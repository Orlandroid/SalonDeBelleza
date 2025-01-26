package com.example.citassalon.presentacion.features.schedule_appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchFlow
import com.example.citassalon.presentacion.features.schedule_appointment.staff.StaffUiState
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.domain.entities.remote.migration.Sucursal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlowMainViewModel : ViewModel() {
    var listOfStaffs: List<Staff> = arrayListOf()
    var listOfServices: List<Service> = arrayListOf()
    var sucursal: Sucursal = Sucursal(id = "", lat = "", long = "", name = "")
    var currentStaff = Staff("", "", "", "", 0)
    var dateAppointment = ""
    var hourAppointment = ""
    var currentFlowBranch: BranchFlow? = null

    private val _branchSideEffects = Channel<ScheduleAppointmentsSideEffects>()
    val branchSideEffects = _branchSideEffects.receiveAsFlow()

    private val _staffUiState: MutableStateFlow<StaffUiState> =
        MutableStateFlow(StaffUiState(listOfStaffs = listOfStaffs, branchName = sucursal.name))
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
                val randomStaff = listOfStaffs.indices.random()
                currentStaff = listOfStaffs[randomStaff]
                sentEvent(ScheduleAppointmentsSideEffects.GoToScheduleService)
            }

            is ScheduleAppointmentEvents.ClickOnImageStaff -> {
                _staffUiState.update { oldState -> oldState.copy(currentStaff = event.staff) }
                sentEvent(ScheduleAppointmentsSideEffects.GoToDetailStaffScreen)
            }

            is ScheduleAppointmentEvents.ClickOnService -> {
                _staffUiState.update { it.copy(listOfServices = listOf(event.service)) }
            }

            is ScheduleAppointmentEvents.ClickOnDate -> {}
            is ScheduleAppointmentEvents.ClickOnTime -> {}
        }
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

    private fun FlowMainViewModel.setCurrentBranch(branch: NegoInfo) {
        _staffUiState.update { oldState ->
            oldState.copy(listOfStaffs = branch.staffs, branchName = branch.sucursal.name)
        }
        sucursal = branch.sucursal
        listOfStaffs = branch.staffs
        listOfServices = branch.services
    }

}

