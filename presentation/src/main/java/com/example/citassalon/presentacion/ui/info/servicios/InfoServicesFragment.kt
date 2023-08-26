package com.example.citassalon.presentacion.ui.info.servicios

import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentInfoServiciosBinding
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment


class InfoServicesFragment :
    BaseFragment<FragmentInfoServiciosBinding>(R.layout.fragment_info_servicios) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Servicios"
    )

    override fun setUpUi() {

    }

}