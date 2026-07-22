package com.example.domain.use_cases

import com.example.domain.entities.remote.migration.Schedule
import com.example.domain.timeprovider.TimeProvider
import javax.inject.Inject

class IsBranchOpenUseCase @Inject constructor(
    private val timeProvider: TimeProvider
) {

    operator fun invoke(schedule: Schedule): Boolean {
        val now = timeProvider.nowInMinutes()

        val morningOpen = schedule.morningOpen.toMinutes()
        val morningClose = schedule.morningClose.toMinutes()
        val afternoonOpen = schedule.afternoonOpen.toMinutes()
        val afternoonClose = schedule.afternoonClose.toMinutes()

        val isMorningOpen = now in morningOpen until morningClose
        val isAfternoonOpen = now in afternoonOpen until afternoonClose

        return isMorningOpen || isAfternoonOpen
    }

    private fun String.toMinutes(): Int {
        val (hour, minute) = split(":").map(String::toInt)
        return hour * 60 + minute
    }
}