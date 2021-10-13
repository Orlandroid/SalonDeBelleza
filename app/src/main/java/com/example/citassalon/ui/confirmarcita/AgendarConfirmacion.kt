package com.example.citassalon.ui.confirmarcita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.databinding.FragmentAgendarConfirmacionBinding
import com.example.citassalon.util.AlertsDialogMessages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarConfirmacion : Fragment(), ListenerAlertDialog {

    private var _binding: FragmentAgendarConfirmacionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelAgendarConfirmacion by viewModels()

    private val args: AgendarConfirmacionArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarConfirmacionBinding.inflate(inflater, container, false)
        binding.buttonConfirmar.setOnClickListener {
            showComfirmAppointment()
        }
        setValuesToView(args)
        return binding.root
    }

    private fun showComfirmAppointment() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showComfirmationAppoinment(this)
    }

    private fun setValuesToView(args: AgendarConfirmacionArgs) {
        binding.cSucursal.text = args.sucursal
        binding.cSatff.text = args.staff.nombre
        binding.cServicio.text = args.servicio.name
        binding.cFecha.text = args.fecha
        binding.cHora.text = args.hora
        binding.cPrecio.text = args.servicio.precio.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clickOnConfirmar() {
        viewModel.saveAppointMent(
            Appointment(
                0,
                args.sucursal,
                args.staff.nombre,
                args.servicio.name,
                args.fecha,
                args.hora,
                args.servicio.precio.toString()
            )
        )
        findNavController().navigate(R.id.action_agendarConfirmacion_to_citaAgendada)
    }

    override fun clickOnCancel() {

    }

}