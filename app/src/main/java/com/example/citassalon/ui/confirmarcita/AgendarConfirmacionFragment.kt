package com.example.citassalon.ui.confirmarcita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.data.models.AppointmentResponse
import com.example.citassalon.databinding.FragmentAgendarConfirmacionBinding
import com.example.citassalon.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.util.AlertsDialogMessages
import com.example.citassalon.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarConfirmacionFragment : Fragment(), ListenerAlertDialogWithButtons {

    private var _binding: FragmentAgendarConfirmacionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelAgendarConfirmacion by viewModels()

    private val args: AgendarConfirmacionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendarConfirmacionBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            buttonConfirmar.setOnClickListener {
                showAlertComfirmAppointment()
            }
            toolbar.toolbarTitle.text = "Agendar Comfirmacion"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setValuesToView(args)
    }

    private fun showAlertComfirmAppointment() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showComfirmationAppoinment(this)
    }

    private fun setValuesToView(args: AgendarConfirmacionFragmentArgs) {
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
        saveToDatabaseAppointMent()
        val action = AgendarConfirmacionFragmentDirections.actionAgendarConfirmacionToCitaAgendada(
            createAppointment()
        )
        navigate(action)
    }

    private fun createAppointment(): AppointmentResponse {
        return AppointmentResponse(
            args.sucursal,
            args.staff.nombre,
            args.servicio.name,
            args.fecha,
            args.hora,
            args.servicio.precio.toString()
        )
    }

    private fun saveToDatabaseAppointMent() {
        /*viewModel.saveAppointMent(
            createAppointment()
        )*/
    }


    override fun clickOnCancel() {

    }

}