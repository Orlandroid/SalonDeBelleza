package com.example.citassalon.presentacion.features.schedule_appointment

sealed class ScheduleAppointmentsSideEffects {
    data object GotoBranchInfo : ScheduleAppointmentsSideEffects()
    data object GoToScheduleStaff : ScheduleAppointmentsSideEffects()
    data object GoToDetailStaffScreen : ScheduleAppointmentsSideEffects()
    data object GoToScheduleService : ScheduleAppointmentsSideEffects()
}