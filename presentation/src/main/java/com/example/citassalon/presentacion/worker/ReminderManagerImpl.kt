package com.example.citassalon.presentacion.worker

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.domain.repository.ReminderManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ReminderManager {

    override fun scheduleReminder(
        serviceName: String,
        branchName: String,
        appointmentDateTime: Long
    ) {
        runCatching {
            val workManager = WorkManager.getInstance(context)


            val oneHourInMillis = TimeUnit.HOURS.toMillis(1)
            val reminderTime = appointmentDateTime - oneHourInMillis
            val delay = reminderTime - System.currentTimeMillis()


            val initialDelay = if (delay > 0) delay else 0L

            val inputData = Data.Builder()
                .putString("service_name", serviceName)
                .putString("branch_name", branchName)
                .build()

            val reminderRequest = OneTimeWorkRequestBuilder<AppointmentReminderWorker>()
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .addTag("reminder_$serviceName")
                .build()

            workManager.enqueue(reminderRequest)
        }.onFailure {
            print(it.message)
        }
    }
}
