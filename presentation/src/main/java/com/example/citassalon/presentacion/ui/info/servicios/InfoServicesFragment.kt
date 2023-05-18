package com.example.citassalon.presentacion.ui.info.servicios

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentInfoServiciosBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment


class InfoServicesFragment :
    BaseFragment<FragmentInfoServiciosBinding>(R.layout.fragment_info_servicios) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Servicios"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}