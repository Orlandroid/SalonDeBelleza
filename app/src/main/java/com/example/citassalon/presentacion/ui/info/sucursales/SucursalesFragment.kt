package com.example.citassalon.presentacion.ui.info.sucursales

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.NegoInfo
import com.example.citassalon.databinding.FragmentSucursalesBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SucursalesFragment : BaseFragment<FragmentSucursalesBinding>(R.layout.fragment_sucursales),
    ClickOnItem<NegoInfo> {

    private val viewModel: SucursalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }


    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = getString(R.string.sucursal)
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.sucursal
        ) {
            binding.shimmerSucursal.gone()
            binding.recyclerView.adapter =
                SucursalAdapter(it, getListener())
        }
    }


    private fun getListener(): ClickOnItem<NegoInfo> = this


    override fun clikOnElement(element: NegoInfo, position: Int?) {
        /* val action = SucursalesFragmentDirections.actionSucursales2ToNegocioInfo(element)
         navigate(action)*/
    }

}