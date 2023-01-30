package com.example.citassalon.presentacion.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.data.preferences.LoginPeferences
import com.example.citassalon.databinding.FragmentHomeBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.ui.extensions.packageName
import com.example.citassalon.presentacion.ui.perfil.userprofile.UserProfileViewModel
import com.example.citassalon.presentacion.util.HOME_TO_NOMBRE_ESTABLECIMEINTO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: UserProfileViewModel by viewModels()

    @Inject
    lateinit var loginPreferences: LoginPeferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            buttonAgendar.setOnClickListener {
                val action = HomeFragmentDirections.actionHome3ToAgendarSucursal()
                navigate(action)
            }
            btnFloatingPerfil.setOnClickListener {
                val action = HomeFragmentDirections.actionHome3ToPerfil()
                navigate(action)
            }
            btnFloatingList.setOnClickListener {
                navigate(HOME_TO_NOMBRE_ESTABLECIMEINTO)
            }
            viewModel.randomUser()
        }
    }

    override fun observerViewModel() {
        observeApiResultGeneric(
            viewModel.randomUserResponse,
            hasProgressTheView = true
        ) { response ->
            loginPreferences.getUserRandomResponse()?.let {
                loginPreferences.saveUserRandomResponse(response)
            }
        }
    }

}