package com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun ScheduleConfirmationScreen(
    navController: NavController, flowMainViewModel: FlowMainViewModel
) {
    BaseComposeScreen(
        navController = navController, toolbarConfiguration = ToolbarConfiguration()
    ) {
        ScheduleConfirmationScreenContent(flowMainViewModel = flowMainViewModel)
    }
}


@Composable
fun ScheduleConfirmationScreenContent(
    modifier: Modifier = Modifier, flowMainViewModel: FlowMainViewModel
) {
    val showConfirmationDialog = remember { mutableStateOf(false) }
    if (showConfirmationDialog.value) {
        ConfirmAppointmentDialog(
            clickOnAccept = { showConfirmationDialog.value = false },
            clickOnCancel = { showConfirmationDialog.value = false }
        )
    }
    Column(
        modifier
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
        ConfirmButton {
            showConfirmationDialog.value = true
        }
    }
}

@Composable
private fun ConfirmButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff051721)),
        onClick = {
            onClick.invoke()
        }
    ) {
        Text(text = stringResource(id = R.string.confirma_cita))
    }
}

@Composable
private fun ButtonImageAndText(
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

@Composable
@Preview(showBackground = true)
fun ScheduleConfirmationScreenContentPreview(modifier: Modifier = Modifier) {
    ScheduleConfirmationScreenContent(flowMainViewModel = FlowMainViewModel())
}