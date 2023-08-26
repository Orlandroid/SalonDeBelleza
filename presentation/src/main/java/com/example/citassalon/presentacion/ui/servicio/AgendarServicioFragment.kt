package com.example.citassalon.presentacion.ui.servicio

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarServicioBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.click
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.showToast
import com.example.citassalon.presentacion.ui.flow_main.FlowMainViewModel
import com.example.domain.entities.remote.migration.Service
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarServicioFragment :
    BaseFragment<FragmentAgendarServicioBinding>(R.layout.fragment_agendar_servicio),
    ClickOnItem<Service>, AlertDialogs.ClickOnAccept {

    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }
    private lateinit var agendarServicioAdapter: AgendarServicioAdapter
    private var listOfServices = arrayListOf<Service>()


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Agendar Servicio"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }


    override fun setUpUi() {
        with(binding) {
            btnNext.click {
                if (!agendarServicioAdapter.isOneItemOrMoreSelect()) {
                    requireContext().showToast("Debes de seleccionar almenos 1 servicio")
                    return@click
                }
                flowMainViewModel.listOfServices = getServicesSelect()
                flowMainViewModel
                val acction =
                    AgendarServicioFragmentDirections.actionAgendarServicioToAgendarFecha()
                navigate(acction)
            }
            agendarServicioAdapter =
                AgendarServicioAdapter(flowMainViewModel.listOfServices, getListener())
            binding.recyclerAgendarServicio.adapter = agendarServicioAdapter
        }
        setValuesToView()
    }

    private fun setValuesToView() {
        with(binding) {
            flowMainViewModel.currentStaff.let {
                Glide.with(requireActivity()).load(it.image_url).into(staffImage)
                nombreStaff.text = it.nombre
            }
        }
    }

    private fun getServicesSelect(): List<Service> {
        return listOfServices.distinct()
    }

    private fun getListener(): ClickOnItem<Service> = this

    override fun clikOnElement(element: Service, position: Int?) {
        binding.tvServicio.text = element.name
        listOfServices.add(element)
    }

    override fun clickOnAccept() {
        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }

}