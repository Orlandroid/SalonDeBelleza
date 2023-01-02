package com.example.citassalon.presentacion.ui.detalle_staff

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.Staff
import com.example.citassalon.databinding.FragmentDetalleStaffBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.flow_main.FlowMainViewModel


class DetalleStaffFragment :
    BaseFragment<FragmentDetalleStaffBinding>(R.layout.fragment_detalle_staff) {

    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

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
        setValueToView(flowMainViewModel.currentStaff)
    }

    private fun setValueToView(staff: Staff) {
        binding.apply {
            Glide.with(requireContext()).load(staff.image_url).into(image)
            name.text = staff.nombre
            ratingBarEvaluation.rating = staff.valoracion.toFloat()
        }
    }

}