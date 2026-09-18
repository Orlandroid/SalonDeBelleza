package com.example.citassalon.presentacion.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class AppointmentReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val serviceName = inputData.getString("service_name") ?: "Appointment"
        val branchName = inputData.getString("branch_name") ?: "Salon"

        showNotification(serviceName, branchName)

        return Result.success()
    }

    private fun showNotification(service: String, branch: String) {
        val channelId = "appointment_reminders"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Appointment Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(com.example.core.R.drawable.ic_baseline_arrow_forward_24)
            .setContentTitle("Upcoming Appointment! 📅")
            .setContentText("Don't forget your $service at $branch in 1 hour.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}