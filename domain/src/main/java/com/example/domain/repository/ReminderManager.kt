package com.example.domain.repository

interface ReminderManager {
    fun scheduleReminder(
        serviceName: String,
        branchName: String,
        appointmentDateTime: Long
    )
}
