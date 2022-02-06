package com.example.citassalon.util


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.citassalon.data.preferences.LoginPeferences
import dagger.assisted.Assisted

@HiltWorker
class SessionWorker(
    private val context: Context,
    workerParams: WorkerParameters,
    private val loginPeferences: LoginPeferences,
) :
    Worker(context, workerParams) {


    override fun doWork(): Result {
        deleteSessionUser()
        return Result.success()
    }

    private fun deleteSessionUser() {
        Log.w("ADNROID", "Eliminado session")
        loginPeferences.destroyUserSession()
        val message =
            "Has estado mucho tiempo en inactividad tu session se eliminara por seguridad"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}