package com.example.domain

data class AvailabilitySlot(
    val time: String,
    val isAvailable: Boolean = true
)