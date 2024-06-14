package com.example.citassalon.presentacion.features.confirmarcita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.toJson
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.domain.mappers.toAppointmentObject
import com.example.domain.perfil.AppointmentFirebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class AgendarConfirmacionFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ListenerAlertDialogWithButtons {

    private val viewModel: AgendarConfirmacionViewModel by viewModels()
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "Agendar Comfirmacion"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AgendarConfirmacionScreen()
            }
        }
    }


    @Composable
    fun AgendarConfirmacionScreen() {
        Column(
            Modifier
                .fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.confirmacionDeCita), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            flowMainViewModel.let { flow ->
                ButtonImageAndText(
                    text = flow.sucursal.name, iconImage = R.drawable.place_24p_negro
                )
                ButtonImageAndText(
                    text = flow.currentStaff.nombre, iconImage = R.drawable.face_unlock_24px
                )
                ButtonImageAndText(
                    text = flow.listOfServices[0].name, iconImage = R.drawable.stars_24px
                )
                ButtonImageAndText(
                    text = flow.dateAppointment, iconImage = R.drawable.insert_invitation_24px
                )
                ButtonImageAndText(
                    text = flow.hourAppointment, iconImage = R.drawable.watch_later_24px
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.Total), fontSize = 20.sp)
                Text(text = flowMainViewModel.listOfServices[0].precio.toString(), fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff051721)),
                onClick = {
                    showAlertComfirmAppointment()
                }) {
                Text(text = stringResource(id = R.string.confirma_cita))
            }
        }
    }

    @Composable
    fun ButtonImageAndText(
        text: String, size: Dp = 24.dp, @DrawableRes iconImage: Int
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AlwaysWhite),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = {

            },
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = iconImage),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(size)
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = text,
                    Modifier.padding(start = 10.dp, top = 8.dp, bottom = 8.dp),
                    color = AlwaysBlack
                )
            }
        }

    }


    override fun setUpUi() {
        viewModel.getAppointments()/*
        buttonConfirmar.click {
            showAlertComfirmAppointment()
        }*/
        setValuesToView()
    }

    private fun showAlertComfirmAppointment() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showComfirmationAppoinment(this)
    }

    private fun setValuesToView() {/*
        with(binding) {
            flowMainViewModel.let {
                cSucursal.text = it.sucursal.name
                cSatff.text = it.currentStaff.nombre
                cServicio.text = it.listOfServices[0].name
                cFecha.text = it.dateAppointment
                cHora.text = it.hourAppointment
                cPrecio.text = it.listOfServices[0].precio.toString()
            }
        }*/
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