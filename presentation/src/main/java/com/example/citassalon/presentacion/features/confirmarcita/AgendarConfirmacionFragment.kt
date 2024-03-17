package com.example.citassalon.presentacion.features.confirmarcita

import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarConfirmacionBinding
import com.example.citassalon.presentacion.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.toJson
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.mappers.toAppointmentObject
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class AgendarConfirmacionFragment :
    BaseFragment<FragmentAgendarConfirmacionBinding>(R.layout.fragment_agendar_confirmacion),
    ListenerAlertDialogWithButtons {

    private val viewModel: AgendarConfirmacionViewModel by viewModels()
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Agendar Comfirmacion"
    )


    override fun setUpUi() {
        viewModel.getAppointments()
        with(binding) {
            buttonConfirmar.click {
                showAlertComfirmAppointment()
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
            createAppointment().toAppointmentObject().toJson()
        )
        navigate(action)
    }

    private fun createAppointment(): AppointmentFirebase {
        val uniqueID = UUID.randomUUID().toString()
        flowMainViewModel.let {
            return AppointmentFirebase(
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