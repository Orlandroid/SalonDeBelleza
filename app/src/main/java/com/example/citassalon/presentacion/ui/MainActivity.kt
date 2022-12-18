package com.example.citassalon.presentacion.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.citassalon.R
import com.example.citassalon.data.preferences.LoginPeferences
import com.example.citassalon.databinding.ActivityMainBinding
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.visible
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.citassalon.presentacion.util.REQUEST_SESSION_MANAGER
import com.example.citassalon.presentacion.util.TASK_SESSION_MANAGER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AlertsDialogMessages.ClickOnAccepSimpleDialog {

    @Inject
    @Named(TASK_SESSION_MANAGER)
    lateinit var taskSessionManager: WorkManager

    @Inject
    @Named(REQUEST_SESSION_MANAGER)
    lateinit var requestSessionManager: OneTimeWorkRequest

    @Inject
    lateinit var loginPeferences: LoginPeferences


    private lateinit var binding: ActivityMainBinding
    private var firstTimeOnView = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showProgress() {
        binding.progressBar.visible()
    }

    fun hideProgress() {
        binding.progressBar.gone()
    }

    override fun onPause() {
        super.onPause()
        taskSessionManager.enqueue(requestSessionManager)
        Log.w("ANDROID", "Ejecutando tarea")
    }

    private fun isSessionActive() {
        if (firstTimeOnView) {
            firstTimeOnView = false
            return
        }
        if (!loginPeferences.getUserSession()) {
            val alert = AlertsDialogMessages(this)
            alert.showSimpleMessage(
                "Imformacion",
                "Haz estado mucho tiempo en inactivad por seguridad tu session se elimino",
                this
            )
        }
    }

    override fun onResume() {
        super.onResume()
        taskSessionManager.cancelWorkById(requestSessionManager.id)
        Log.w("ANDROID", "Cancelando tarea")
        isSessionActive()
    }

    override fun clikOnPositiveButton() {
        Log.w("ANDROID", "Regresando al login")
        findNavController(R.id.fragment).popBackStack(R.id.login, true)
    }

}