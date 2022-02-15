package com.example.citassalon.util


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.citassalon.data.preferences.LoginPeferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val loginPeferences: LoginPeferences
) :
    Worker(context, workerParams) {


    override fun doWork(): Result {
        deleteSessionUser()
        return Result.success()
    }

    private fun deleteSessionUser() {
        Log.w("ANDROID", "Eliminado session")
        loginPeferences.destroyUserSession()
        Log.w("ANDROID", loginPeferences.getUserSession().toString())

    }

}