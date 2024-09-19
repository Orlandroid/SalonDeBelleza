package com.example.citassalon.presentacion.features.schedule_appointment

sealed class ScheduleAppointmentScreens(val route: String) {
    data object Home : ScheduleAppointmentScreens("home")
    data object ChoseBranch : ScheduleAppointmentScreens("chose_branch")
    data object ScheduleStaff : ScheduleAppointmentScreens("schedule_staff")
    data object DetailStaff : ScheduleAppointmentScreens("detail_staff")
    data object Services : ScheduleAppointmentScreens("services")
    data object Schedule : ScheduleAppointmentScreens("schedule")
}