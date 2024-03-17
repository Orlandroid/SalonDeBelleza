package com.example.citassalon.presentacion.features.detalle_staff

import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentDetalleStaffBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.domain.entities.remote.migration.Staff


class DetalleStaffFragment :
    BaseFragment<FragmentDetalleStaffBinding>(R.layout.fragment_detalle_staff) {

    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Detalle Staff"
    )


    override fun setUpUi() {
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