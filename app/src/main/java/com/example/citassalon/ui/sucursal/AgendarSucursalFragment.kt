package com.example.citassalon.ui.sucursal


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.Sucursal
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.gone
import com.example.citassalon.ui.extensions.navigate
import com.example.citassalon.ui.extensions.visible
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalViewModel
import com.example.citassalon.util.ERROR_SERVIDOR
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
                        clikOnAccept = getListenerDialog()
                    )
                    activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
                }
                is ApiState.ErrorNetwork -> {
                    Log.w("ERROR",apiState.message.toString())
                    val dialog =
                        AlertDialogs(
                            AlertDialogs.ERROR_MESSAGE,
                            "Verifica tu conexion de internet",
                            clikOnAccept = object : AlertDialogs.ClickOnAccept {
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

    private fun getListener(): ClickOnItem<Sucursal> = this

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this


    override fun clikOnElement(element: Sucursal, position: Int?) {
        with(binding) {
            textAgendarSucursal.text = element.name
            val action = AgendarSucursalFragmentDirections.actionAgendarSucursalToAgendarStaff(
                textAgendarSucursal.text.toString()
            )
            navigate(action)
        }
    }

    override fun clickOnAccept() {
        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }


}