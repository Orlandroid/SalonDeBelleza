package com.example.citassalon.presentacion.ui.sucursal


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.NegoInfo
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.visible
import com.example.citassalon.presentacion.ui.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalViewModel
import com.example.citassalon.presentacion.util.ERROR_SERVIDOR
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgendarSucursalFragment :
    BaseFragment<FragmentAgendarSucursalBinding>(R.layout.fragment_agendar_sucursal),
    ClickOnItem<NegoInfo>, AlertDialogs.ClickOnAccept {

    private val viewModel: SucursalViewModel by viewModels()
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = getString(R.string.agendar_sucursal)
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.sucursal.observe(viewLifecycleOwner) { apiState ->
            if (apiState is ApiState.Loading) {
                binding.shimmerSucursal.visible()
                binding.recyclerSucursal.visible()
            } else {
                binding.shimmerSucursal.gone()
            }
            when (apiState) {
                is ApiState.Success -> {
                    if (apiState.data != null) {
                        with(binding) {
                            recyclerSucursal.adapter = SucursalAdapter(apiState.data, getListener())
                        }
                    }
                }
                is ApiState.NoData -> {
                    binding.noData.visible()
                }
                is ApiState.Error -> {
                    val alert = AlertDialogs(
                        messageBody = ERROR_SERVIDOR,
                        kindOfMessage = AlertDialogs.ERROR_MESSAGE,
                        clickOnAccept = getListenerDialog()
                    )
                    activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
                }
                is ApiState.ErrorNetwork -> {
                    val dialog =
                        AlertDialogs(
                            AlertDialogs.ERROR_MESSAGE,
                            "Verifica tu conexion de internet",
                            clickOnAccept = object : AlertDialogs.ClickOnAccept {
                                override fun clickOnAccept() {
                                    findNavController().popBackStack()
                                }

                                override fun clickOnCancel() {

                                }

                            })
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
                else -> {}
            }
        }
    }

    private fun getListener(): ClickOnItem<NegoInfo> = this

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this


    override fun clikOnElement(element: NegoInfo, position: Int?) {
        with(binding) {
            flowMainViewModel.let {
                it.sucursal = element.sucursal
                it.listOfStaffs = element.staffs
                it.listOfServices = element.services
            }
            textAgendarSucursal.text = element.sucursal.name
            val action = AgendarSucursalFragmentDirections.actionAgendarSucursalToAgendarStaff()
            navigate(action)
        }
    }

    override fun clickOnAccept() {
        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }


}