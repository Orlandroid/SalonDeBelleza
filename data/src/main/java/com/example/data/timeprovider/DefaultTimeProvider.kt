package com.example.data.timeprovider

import com.example.domain.timeprovider.TimeProvider
import jakarta.inject.Inject
import java.util.Calendar

class DefaultTimeProvider @Inject constructor() : TimeProvider {

    override fun nowInMinutes(): Int {
        val calendar = Calendar.getInstance()

        return calendar.get(Calendar.HOUR_OF_DAY) * 60 +
                calendar.get(Calendar.MINUTE)
    }
}