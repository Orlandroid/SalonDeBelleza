package com.example.citassalon.presentacion.features


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.citassalon.R
import com.example.citassalon.databinding.ActivityMainBinding
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.gone
import com.example.citassalon.presentacion.features.extensions.visible
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.data.preferences.LoginPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AlertsDialogMessages.ClickOnAccepSimpleDialog {


    @Inject
    lateinit var loginPeferences: LoginPreferences

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun setToolbarConfiguration(configuration: ToolbarConfiguration) {
        setOnClickBackButton(configuration.clickOnBack)
        changeTitleToolbar(configuration.toolbarTitle)
        showToolbar(configuration.showToolbar)
    }

    private fun setOnClickBackButton(clickOnBack: (() -> Unit)?) = with(binding) {
        val clickOnBackButton = if (clickOnBack == null) {
            {
                navController?.popBackStack()
            }
        } else {
            {
                clickOnBack()
            }
        }
        toolbarLayout.toolbarBack.click {
            clickOnBackButton()
        }
    }

    private fun changeTitleToolbar(title: String) {
        binding.toolbarLayout.toolbarTitle.text = title
    }

    private fun showToolbar(shouldShow: Boolean) {
        if (shouldShow) {
            binding.toolbarLayout.root.visible()
        } else {
            binding.toolbarLayout.root.gone()
        }
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

    data class ToolbarConfiguration(
        val showToolbar: Boolean = false,
        val clickOnBack: (() -> Unit)? = null,
        val toolbarTitle: String = ""
    )

}