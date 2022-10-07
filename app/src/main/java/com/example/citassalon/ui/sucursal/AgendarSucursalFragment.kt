package com.example.citassalon.ui.sucursal


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Sucursal
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.extensions.*
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.ui.share_beetwen_sucursales.SucursalViewModel
import com.example.citassalon.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgendarSucursalFragment : Fragment(), ClickOnItem<Sucursal>, AlertDialogs.ClickOnAccept {


    private var _binding: FragmentAgendarSucursalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SucursalViewModel by viewModels()


    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendarSucursalBinding.inflate(inflater)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Agendar Sucursal"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setUpObserves()
    }

    private fun getListener(): ClickOnItem<Sucursal> = this

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this


    private fun setUpObserves() {
        viewModel.sucursal.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        with(binding) {
                            shimmerSucursal.gone()
                            recyclerSucursal.adapter = SucursalAdapter(it.data, getListener())
                        }
                    }
                }
                is ApiState.Loading -> {
                    with(binding) {
                        itemNoDataNoNetwork.itemNoDataNoNetworkContainer.gone()
                        recyclerSucursal.visible()
                        shimmerSucursal.visible()
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
                        recyclerSucursal.gone()
                        shimmerSucursal.gone()
                        itemNoDataNoNetwork.message.text = "Error de conexion"
                        itemNoDataNoNetwork.imageNoDataNoNetwork.setImageResource(
                            getRandomErrorNetworkImage()
                        )
                    }
                    snackErrorConection()
                }
            }
        }
    }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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