package com.example.citassalon.presentacion.main


import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.data.preferences.LoginPreferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val loginPreferences: LoginPreferences
) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            deleteSessionUser()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun deleteSessionUser() {
        loginPreferences.destroyUserSession()
    }

}