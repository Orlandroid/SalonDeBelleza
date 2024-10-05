package com.example.citassalon.presentacion.features.profile.historial_citas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Card
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.domain.perfil.AppointmentFirebase
import com.example.domain.state.ApiState

@Composable
fun AppointmentHistoryScreen(navController: NavHostController) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            title = stringResource(id = R.string.historiasl_de_citas)
        )
    ) {
        DatingHistory()
    }
}


@Composable
fun DatingHistory(
    viewModel: HistorialCitasViewModel = hiltViewModel()
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val appointmentState = viewModel.appointment.observeAsState()
    val removeAppointmentState = viewModel.removeAppointment.observeAsState()
    when (appointmentState.value) {
        is ApiState.Success -> {
            Column(Modifier.fillMaxSize()) {
                appointmentState.value?.data?.let { appointments ->
                    if (appointments.isEmpty()) {
                        NotDatView()
                    } else {
                        LazyColumn {
                            appointmentState.value?.data?.forEach { appointment ->
                                item {
                                    ItemAppointment(
                                        appointment,
                                        onRemoveAppointment = {
                                            openAlertDialog.value = true
                                            viewModel.removeAppointment(appointment.idAppointment)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }

        is ApiState.NoData -> {
            NotDatView()
        }

        is ApiState.Loading -> {
            ProgressDialog()
        }

        else -> {}
    }
    if (openAlertDialog.value) {
        BaseAlertDialogMessages(
            onDismissRequest = {
                openAlertDialog.value = false
            },
            onConfirmation = {
                openAlertDialog.value = false
            },
            alertDialogMessagesConfig = AlertDialogMessagesConfig(
                title = R.string.delete_row_message,
                bodyMessage = R.string.delete_row_message,
                kindOfMessage = KindOfMessage.WARING
            )
        )
    }

}

@Composable
fun ItemAppointment(
    appointment: AppointmentFirebase,
    onRemoveAppointment: () -> Unit
) {
    Card(modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { }) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                painter = painterResource(id = R.drawable.tienda),
                contentDescription = "ImageAppointment"
            )
            Text(text = appointment.service, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = appointment.establishment, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(color = Color.Red.copy(alpha = .80f),
                text = "Remove",
                fontSize = 24.sp,
                modifier = Modifier.clickable {
                    onRemoveAppointment.invoke()
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ItemAppointmentPreview() {
    val mAppointment = AppointmentFirebase(
        establishment = "establishment",
        service = "service",
    )
    ItemAppointment(
        appointment = mAppointment,
        onRemoveAppointment = {}
    )
}

@Composable
fun NotDatView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            getRandomNoDataAnimation()
        )
    )
    Box(Modifier.fillMaxSize()) {
        LottieAnimation(
            iterations = LottieConstants.IterateForever,
            composition = composition,
            modifier = Modifier
                .height(250.dp)
                .width(250.dp),
            alignment = Alignment.Center
        )
    }
}

private fun getRandomNoDataAnimation(): Int = when ((1..3).random()) {
    1 -> {
        R.raw.no_data_animation
    }

    2 -> {
        R.raw.no_data_available
    }

    else -> R.raw.no_data_found
}