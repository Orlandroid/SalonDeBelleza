package com.example.citassalon.ui.confirmarcita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.data.mappers.toAppointmentObject
import com.example.citassalon.data.mappers.toAppointmentRemote
import com.example.citassalon.data.models.remote.Appointment
import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.databinding.FragmentAgendarConfirmacionBinding
import com.example.citassalon.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.util.AlertsDialogMessages
import com.example.citassalon.ui.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AgendarConfirmacionFragment : Fragment(), ListenerAlertDialogWithButtons {

    private var _binding: FragmentAgendarConfirmacionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AgendarConfirmacionViewModel by viewModels()

    private val args: AgendarConfirmacionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendarConfirmacionBinding.inflate(inflater, container, false)
        setUpUi()
        viewModel.getAppointments()
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
        with(binding){
            cSucursal.text = args.sucursal
            cSatff.text = args.staff.nombre
            cServicio.text = args.servicio.name
            cFecha.text = args.fecha
            cHora.text = args.hora
            cPrecio.text = args.servicio.precio.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clickOnConfirmar() {
        saveToDatabaseAppointMent()
        val action = AgendarConfirmacionFragmentDirections.actionAgendarConfirmacionToCitaAgendada(createAppointment().toAppointmentObject())
        navigate(action)
    }

    private fun createAppointment(): Appointment {
        val uniqueID = UUID.randomUUID().toString()
        return Appointment(
            uniqueID,
            args.sucursal,
            args.staff.nombre,
            args.servicio.name,
            args.fecha,
            args.hora,
            args.servicio.precio.toString()
        )
    }

    private fun saveToDatabaseAppointMent() {
        viewModel.saveAppointMent(createAppointment())
    }


    override fun clickOnCancel() {

    }

}