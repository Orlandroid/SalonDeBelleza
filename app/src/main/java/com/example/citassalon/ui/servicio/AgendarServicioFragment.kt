package com.example.citassalon.ui.servicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.databinding.FragmentAgendarServicioBinding
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.util.ERROR_SERVIDOR
import com.example.citassalon.ui.extensions.action
import com.example.citassalon.ui.extensions.displaySnack
import com.example.citassalon.ui.extensions.navigate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarServicioFragment : Fragment(), ClickOnItem<Servicio> ,AlertDialogs.ClickOnAccept{


    private var _binding: FragmentAgendarServicioBinding? = null
    private val binding get() = _binding!!
    private val viewModelAgendarServicio: AgendarServicioViewModel by viewModels()


    private val args: AgendarServicioFragmentArgs by navArgs()
    private var currentServicio: Servicio? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendarServicioBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Agendar Servicio"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setUpObservers()
        setValuesToView(args)
    }

    private fun setValuesToView(args: AgendarServicioFragmentArgs) {
        binding.sucursal.text = args.sucursal
        binding.staffImage.setImageResource(args.staff.getResourceImage())
        binding.nombreStaff.text = args.staff.nombre
    }

    private fun getListener():ClickOnItem<Servicio> = this

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this

    private fun setUpObservers() {
        viewModelAgendarServicio.services.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    binding.shimmerServicio.visibility=View.VISIBLE
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.shimmerServicio.visibility = View.GONE
                        binding.recyclerAgendarServicio.adapter = AgendarServicioAdapter(it.data, getListener())
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clikOnElement(element: Servicio, position: Int?) {
        binding.tvServicio.text = element.name
        currentServicio = element
        val acction = AgendarServicioFragmentDirections.actionAgendarServicioToAgendarFecha(
            args.sucursal,
            args.staff,
            element
        )
        navigate(acction)
    }

    override fun clikOnAccept() {
        findNavController().popBackStack()
    }

    override fun clikOnCancel() {

    }

}