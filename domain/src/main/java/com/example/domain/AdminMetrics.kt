package com.example.domain

data class AdminMetrics(
    val totalBookings: Int = 0,
    val totalRevenue: Double = 0.0,
    val activeClientsCount: Int = 0,
    val pendingAppointmentsCount: Int = 0
)

data class AdminAppointmentUiModel(
    val id: String,
    val clientName: String,
    val serviceName: String,
    val staffName: String,
    val date: String,
    val time: String,
    val status: String
)