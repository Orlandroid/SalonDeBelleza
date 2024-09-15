package com.example.citassalon.presentacion.features.schedule_appointment

sealed class ScheduleAppointmentScreens(val route: String) {
    data object HomeScreen : ScheduleAppointmentScreens("home_screen")
}