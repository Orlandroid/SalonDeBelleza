package com.example.citassalon.presentacion.features.schedule_appointment

import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff

sealed class ScheduleAppointmentEvents {
    data class ClickOnBranch(val branch: NegoInfo) : ScheduleAppointmentEvents()
    data class ClickOnStaff(val staff: Staff) : ScheduleAppointmentEvents()
    data object OnRandomStaff : ScheduleAppointmentEvents()
    data class ClickOnImageStaff(val staff: Staff) : ScheduleAppointmentEvents()
    data class ClickOnService(val service: Service) : ScheduleAppointmentEvents()
    data class ClickOnDate(val service: Service) : ScheduleAppointmentEvents()
    data class ClickOnTime(val service: Service) : ScheduleAppointmentEvents()
}