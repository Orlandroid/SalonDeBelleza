package com.example.citassalon.main


import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.citassalon.data.preferences.LoginPeferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val loginPeferences: LoginPeferences
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
        loginPeferences.destroyUserSession()
    }

}