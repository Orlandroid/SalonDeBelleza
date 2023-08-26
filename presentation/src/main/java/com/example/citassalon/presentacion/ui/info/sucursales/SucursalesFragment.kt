package com.example.citassalon.presentacion.ui.info.sucursales

import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentSucursalesBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalViewModel
import com.example.domain.entities.remote.migration.NegoInfo
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SucursalesFragment : BaseFragment<FragmentSucursalesBinding>(R.layout.fragment_sucursales),
    ClickOnItem<NegoInfo> {

    private val viewModel: SucursalViewModel by viewModels()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.ubicacion)
    )


    override fun setUpUi() {

    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.sucursal
        ) {
            binding.shimmerSucursal.gone()
            binding.recyclerView.adapter = SucursalAdapter(it, getListener())
        }
    }


    private fun getListener(): ClickOnItem<NegoInfo> = this


    override fun clikOnElement(element: NegoInfo, position: Int?) {
        val gson = Gson()
        val bussnicesInfo = gson.toJson(element)
        val action = SucursalesFragmentDirections.actionSucursales2ToNegocioInfo(bussnicesInfo)
        navigate(action)
    }

}