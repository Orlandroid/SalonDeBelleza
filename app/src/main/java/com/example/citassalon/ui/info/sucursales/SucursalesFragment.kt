package com.example.citassalon.ui.info.sucursales

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Sucursal
import com.example.citassalon.databinding.FragmentSucursalesBinding
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalViewModel
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.gone
import com.example.citassalon.ui.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SucursalesFragment : BaseFragment<FragmentSucursalesBinding>(R.layout.fragment_sucursales),
    ClickOnItem<Sucursal> {

    private val viewModel: SucursalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }


    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Sucursales"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.sucursal.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.shimmerSucursal.gone()
                        binding.recyclerView.adapter =
                            SucursalAdapter(it.data, getListener())
                    }
                }
                is ApiState.Loading -> {

                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
                is ApiState.NoData -> {

                }
            }
        }
    }


    private fun getListener(): ClickOnItem<Sucursal> = this


    override fun clikOnElement(element: Sucursal, position: Int?) {
        val action = SucursalesFragmentDirections.actionSucursales2ToNegocioInfo(element)
        navigate(action)
    }

}