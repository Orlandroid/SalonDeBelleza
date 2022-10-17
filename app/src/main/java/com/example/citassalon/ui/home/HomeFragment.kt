package com.example.citassalon.ui.home

import android.os.Bundle
import android.view.*
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHomeBinding
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.navigate
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
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
        }
    }

}