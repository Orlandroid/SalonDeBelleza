package com.example.citassalon.presentacion.ui.info.nuestro_staff

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNuestroStaffBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment


class NuestroStaffFragment :
    BaseFragment<FragmentNuestroStaffBinding>(R.layout.fragment_nuestro_staff) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Nuestro Staff"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


}