package com.example.citassalon.ui.servicio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.databinding.FragmentAgendarServicioBinding
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.*
import com.example.citassalon.util.ERROR_SERVIDOR
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarServicioFragment :
    BaseFragment<FragmentAgendarServicioBinding>(R.layout.fragment_agendar_servicio),
    ClickOnItem<Servicio>, AlertDialogs.ClickOnAccept {

    private val viewModelAgendarServicio: AgendarServicioViewModel by viewModels()
    private val args: AgendarServicioFragmentArgs by navArgs()
    private var currentServicio: Servicio? = null
    private lateinit var agendarServicioAdapter: AgendarServicioAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }


    override fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Agendar Servicio"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnNext.click {
                if (!agendarServicioAdapter.isOneItemOrMoreSelect()) {
                    requireContext().showToast("Debes de seleccionar almenos 1 servicio")
                    return@click
                }
                currentServicio?.let {
                    val acction =
                        AgendarServicioFragmentDirections.actionAgendarServicioToAgendarFecha(
                            args.sucursal,
                            args.staff,
                            currentServicio!!
                        )
                    navigate(acction)
                }
            }
        }
        setValuesToView(args)
    }

    private fun setValuesToView(args: AgendarServicioFragmentArgs) {
        with(binding) {
            sucursal.text = args.sucursal
            staffImage.setImageResource(args.staff.getResourceImage())
            nombreStaff.text = args.staff.nombre
        }
    }

    private fun getListener(): ClickOnItem<Servicio> = this

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this

    override fun observerViewModel() {
        super.observerViewModel()
        viewModelAgendarServicio.services.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    binding.shimmerServicio.visible()
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.shimmerServicio.gone()
                        agendarServicioAdapter = AgendarServicioAdapter(it.data, getListener())
                        binding.recyclerAgendarServicio.adapter = agendarServicioAdapter
                    }
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
                    snackErrorConection()
                }
                else -> {}
            }
        }
    }

    private fun snackErrorConection() {
        binding.root.displaySnack(
            getString(R.string.network_error),
            Snackbar.LENGTH_INDEFINITE
        ) {
            action(getString(R.string.retry)) {
                viewModelAgendarServicio.getServices()
            }
        }
    }

    override fun clikOnElement(element: Servicio, position: Int?) {
        binding.tvServicio.text = element.name
        currentServicio = element
    }

    override fun clikOnAccept() {
        findNavController().popBackStack()
    }

    override fun clikOnCancel() {

    }

}