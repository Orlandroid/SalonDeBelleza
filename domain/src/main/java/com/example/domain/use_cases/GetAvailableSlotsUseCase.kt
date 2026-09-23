package com.example.domain.use_cases


import com.example.domain.AvailabilitySlot
import com.example.domain.entities.remote.migration.Schedule
import javax.inject.Inject

class GetAvailableSlotsUseCase @Inject constructor() {

    operator fun invoke(
        schedule: Schedule,
        bookedSlots: List<String> = emptyList()
    ): List<AvailabilitySlot> {
        val slots = mutableListOf<AvailabilitySlot>()


        slots.addAll(generateSlots(schedule.morningOpen, schedule.morningClose, bookedSlots))


        slots.addAll(generateSlots(schedule.afternoonOpen, schedule.afternoonClose, bookedSlots))

        return slots
    }

    private fun generateSlots(
        startTime: String,
        endTime: String,
        bookedSlots: List<String>
    ): List<AvailabilitySlot> {
        val timeList = mutableListOf<AvailabilitySlot>()
        var current = startTime
        while (current < endTime) {
            val isBooked = bookedSlots.contains(current)
            timeList.add(AvailabilitySlot(time = current, isAvailable = !isBooked))
            current = add30Minutes(current)
        }
        return timeList
    }

    private fun add30Minutes(time: String): String {
        val parts = time.split(":")
        var hour = parts[0].toInt()
        var min = parts[1].toInt() + 30

        if (min >= 60) {
            hour += 1
            min = 0
        }
        return "%02d:%02d".format(hour, min)
    }
}