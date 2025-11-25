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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.schedule_appointment.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.staff.StaffUiState
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.migration.Staff

@Composable
fun ScheduleConfirmationScreen(
    navController: NavController,
    flowMainViewModel: FlowMainViewModel,
    agendarConfirmacionViewModel: AgendarConfirmacionViewModel = hiltViewModel(),
    navigateToAppointmentSchedule: () -> Unit
) {
    val uiState = flowMainViewModel.staffUiState.collectAsStateWithLifecycle()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.confirmar_cita))
    ) {
        ScheduleConfirmationScreenContent(
            staffUiState = uiState.value,
            servicePrice = flowMainViewModel.listOfServices[0].precio.toString(),
            dateAppointment = flowMainViewModel.dateAppointment,
            hourAppointment = flowMainViewModel.hourAppointment,
            saveAppointment = {
                agendarConfirmacionViewModel.saveAppointment(
                    flowMainViewModel.getAppointmentFirebase()
                )
            },
            navigateToAppointmentSchedule = navigateToAppointmentSchedule
        )
    }
}


@Composable
fun ScheduleConfirmationScreenContent(
    modifier: Modifier = Modifier,
    staffUiState: StaffUiState,
    servicePrice: String,
    dateAppointment: String,
    hourAppointment: String,
    saveAppointment: () -> Unit,
    navigateToAppointmentSchedule: () -> Unit
) {
    val showConfirmationDialog = remember { mutableStateOf(false) }
    if (showConfirmationDialog.value) {
        ConfirmAppointmentDialog(
            clickOnAccept = {
                showConfirmationDialog.value = false
                navigateToAppointmentSchedule.invoke()
            },
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
        ButtonImageAndText(
            text = staffUiState.branchName,
            iconImage = R.drawable.place_24p_negro
        )
        ButtonImageAndText(
            text = staffUiState.currentStaff?.nombre.orEmpty(),
            iconImage = R.drawable.face_unlock_24px
        )
        ButtonImageAndText(
            text = staffUiState.listOfServices[0].name,
            iconImage = R.drawable.stars_24px
        )
        ButtonImageAndText(
            text = dateAppointment,
            iconImage = R.drawable.insert_invitation_24px
        )
        ButtonImageAndText(
            text = hourAppointment,
            iconImage = R.drawable.watch_later_24px
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text =
                stringResource(id = R.string.Total),
                fontSize = 20.sp
            )
            Text(
                text = servicePrice,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        ConfirmButton {
            showConfirmationDialog.value = true
            saveAppointment.invoke()
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
    text: String, size: Dp = 24.dp,
    @DrawableRes iconImage: Int
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
fun ScheduleConfirmationScreenContentPreview() {
    ScheduleConfirmationScreenContent(
        servicePrice = "150",
        dateAppointment = "12/09/2024",
        hourAppointment = "12:30 am",
        saveAppointment = {},
        navigateToAppointmentSchedule = {},
        staffUiState = StaffUiState(
            branchName = "Zacatecas",
            currentStaff = Staff.mockStaff()
        )
    )
}