package com.example.citassalon.presentacion.ui.confirmarcita

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.citassalon.R
import com.example.citassalon.data.mappers.toAppointmentObject
import com.example.citassalon.data.models.remote.Appointment
import com.example.citassalon.databinding.FragmentAgendarConfirmacionBinding
import com.example.citassalon.presentacion.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AgendarConfirmacionFragment :
    BaseFragment<FragmentAgendarConfirmacionBinding>(R.layout.fragment_agendar_confirmacion),
    ListenerAlertDialogWithButtons {

    private val viewModel: AgendarConfirmacionViewModel by viewModels()
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        viewModel.getAppointments()
        with(binding) {
            buttonConfirmar.setOnClickListener {
                showAlertComfirmAppointment()
            }
            toolbar.toolbarTitle.text = "Agendar Comfirmacion"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setValuesToView()
    }

    private fun showAlertComfirmAppointment() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showComfirmationAppoinment(this)
    }

    private fun setValuesToView() {
        with(binding) {
            flowMainViewModel.let {
                cSucursal.text = it.sucursal.name
                cSatff.text = it.currentStaff.nombre
                cServicio.text = it.listOfServices[0].name
                cFecha.text = it.dateAppointment
                cHora.text = it.hourAppointment
                cPrecio.text = it.listOfServices[0].precio.toString()
            }
        }
    }

    override fun clickOnConfirmar() {
        saveToDatabaseAppointment()
        val action = AgendarConfirmacionFragmentDirections.actionAgendarConfirmacionToCitaAgendada(
            createAppointment().toAppointmentObject()
        )
        navigate(action)
    }

    private fun createAppointment(): Appointment {
        val uniqueID = UUID.randomUUID().toString()
        flowMainViewModel.let {
            return Appointment(
                uniqueID,
                it.sucursal.name,
                it.currentStaff.nombre,
                it.listOfServices[0].name,
                it.dateAppointment,
                it.hourAppointment,
                it.listOfServices[0].precio.toString()
            )
        }
    }

    private fun saveToDatabaseAppointment() {
        viewModel.saveAppointMent(createAppointment())
    }

    override fun clickOnCancel() {

    }

}