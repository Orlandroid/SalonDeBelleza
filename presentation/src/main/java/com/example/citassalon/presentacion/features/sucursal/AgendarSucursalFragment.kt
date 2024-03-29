package com.example.citassalon.presentacion.features.sucursal


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.gone
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.extensions.visible
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.presentacion.features.share_beetwen_sucursales.SucursalViewModel
import com.example.domain.entities.remote.migration.NegoInfo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgendarSucursalFragment :
    BaseFragment<FragmentAgendarSucursalBinding>(R.layout.fragment_agendar_sucursal),
    ClickOnItem<NegoInfo>, AlertDialogs.ClickOnAccept {

    private val viewModel: SucursalViewModel by viewModels()
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.agendar_sucursal)
    )

    override fun setUpUi() {

    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.sucursal,
            onLoading = {
                binding.shimmerSucursal.visible()
                binding.recyclerSucursal.visible()
            },
            onFinishLoading = {
                binding.shimmerSucursal.gone()
            },
            hasProgressTheView = false,
            shouldCloseTheViewOnApiError = true
        ) {
            binding.recyclerSucursal.adapter = SucursalAdapter(it, getListener())
        }
    }

    private fun getListener(): ClickOnItem<NegoInfo> = this


    override fun clickOnItem(element: NegoInfo, position: Int?) {
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