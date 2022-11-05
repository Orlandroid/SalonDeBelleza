package com.example.citassalon.ui.sucursal


import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Sucursal
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.*
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalViewModel
import com.example.citassalon.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgendarSucursalFragment :
    BaseFragment<FragmentAgendarSucursalBinding>(R.layout.fragment_agendar_sucursal),
    ClickOnItem<Sucursal>, AlertDialogs.ClickOnAccept {

    private val viewModel: SucursalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Agendar Sucursal"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.sucursal.observe(viewLifecycleOwner) {
            if (it is ApiState.Loading) {
                binding.itemNoDataNoNetwork.itemNoDataNoNetworkContainer.gone()
                binding.shimmerSucursal.visible()
                binding.recyclerSucursal.visible()
            } else {
                binding.shimmerSucursal.gone()
            }
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        with(binding) {
                            recyclerSucursal.adapter = SucursalAdapter(it.data, getListener())
                        }
                    }
                }
                is ApiState.NoData -> {
                    binding.itemNoDataNoNetwork.imageNoDataNoNetwork.setImageResource(
                        getRandomNoDataImage()
                    )
                }
                is ApiState.Error -> {
                    val alert = AlertDialogs(
                        messageBody = ERROR_SERVIDOR,
                        kindOfMessage = AlertDialogs.ERROR_MESSAGE,
                        clikOnAccept = getListenerDialog()
                    )
                    activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
                }
                is ApiState.ErrorNetwork -> {
                    with(binding) {
                        itemNoDataNoNetwork.message.text = "Error de conexion"
                        itemNoDataNoNetwork.imageNoDataNoNetwork.setImageResource(
                            getRandomErrorNetworkImage()
                        )
                    }
                    snackErrorConection()
                }
                else -> {}
            }
        }
    }

    private fun getListener(): ClickOnItem<Sucursal> = this

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this


    private fun snackErrorConection() {
        binding.root.displaySnack(
            getString(R.string.network_error),
            Snackbar.LENGTH_INDEFINITE
        ) {
            action(getString(R.string.retry)) {
                viewModel.getSucursales()
            }
        }
    }


    override fun clikOnElement(element: Sucursal, position: Int?) {
        with(binding) {
            textAgendarSucursal.text = element.name
            val action = AgendarSucursalFragmentDirections.actionAgendarSucursalToAgendarStaff(
                textAgendarSucursal.text.toString()
            )
            navigate(action)
        }
    }

    override fun clikOnAccept() {
        findNavController().popBackStack()
    }

    override fun clikOnCancel() {

    }


}