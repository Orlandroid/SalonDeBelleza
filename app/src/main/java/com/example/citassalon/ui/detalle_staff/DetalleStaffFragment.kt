package com.example.citassalon.ui.detalle_staff

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.databinding.FragmentDetalleStaffBinding
import com.example.citassalon.ui.base.BaseFragment


class DetalleStaffFragment :
    BaseFragment<FragmentDetalleStaffBinding>(R.layout.fragment_detalle_staff) {

    private val args by navArgs<DetalleStaffFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Detalle Staff"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        getFromArgs()
    }

    private fun getFromArgs() {
        setValueToView(args.currentStaff)
    }

    private fun setValueToView(staff: Staff) {
        binding.apply {
            image.setImageResource(staff.getResourceImage())
            name.text = staff.nombre
            ratingBarEvaluation.rating = staff.valoracion
        }
    }

}