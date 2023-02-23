package com.example.citassalon.presentacion.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.preferences.LoginPeferences
import com.example.citassalon.databinding.ActivityMainBinding
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.visible
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AlertsDialogMessages.ClickOnAccepSimpleDialog {


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
        if (!binding.progressBar.isVisible) {
            binding.progressBar.visible()
        }
    }

    fun hideProgress() {
        if (binding.progressBar.isVisible) {
            binding.progressBar.gone()
        }
    }

    fun shouldShowProgress(isLoading: Boolean) {
        if (isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    override fun clikOnPositiveButton() {
        findNavController(R.id.fragment).popBackStack(R.id.login, true)
    }

}