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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.ErrorState
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.IsTwoButtonsAlert
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.domain.perfil.Appointment

@Composable
fun AppointmentHistoryScreen(
    navController: NavHostController,
    viewModel: AppointmentHistoryViewModel = hiltViewModel()
) {
    val viewState = viewModel.state.collectAsStateWithLifecycle()
    when (viewState.value) {
        AppointmentHistoryViewState.OnLoading -> {
            ProgressDialog()
        }

        is AppointmentHistoryViewState.OnContent -> {
            //Todo when whe don,t have data add NotDatView
            AppointmentHistoryScreenContent(
                uiState = (viewState.value as AppointmentHistoryViewState.OnContent).content,
                onEvents = viewModel::onEvents,
                navHostController = navController
            )
        }

        is AppointmentHistoryViewState.OnError -> {
            ErrorState(AlertDialogMessagesConfig(bodyMessage = "Error"))//Todo migrate
        }

    }
}

@Composable
private fun AppointmentHistoryScreenContent(
    uiState: AppointmentHistoryUiState,
    onEvents: (event: AppointmentHistoryEvents) -> Unit,
    navHostController: NavHostController
) {
    BaseComposeScreen(
        navController = navHostController, toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true, title = stringResource(id = R.string.historiasl_de_citas),
        )
    ) {
        AppointHistoryList(
            appointments = uiState.appointments,
            onEvents = onEvents
        )
    }
    if (uiState.showDialog) {
        ShowDialogDeleteAppointment(
            onEvents = onEvents
        )
    }
}

@Composable
private fun ShowDialogDeleteAppointment(
    onEvents: (event: AppointmentHistoryEvents) -> Unit
) {
    BaseAlertDialogMessages(
        onDismissRequest = {
            onEvents(AppointmentHistoryEvents.OnCancel)
        }, alertDialogMessagesConfig = AlertDialogMessagesConfig(
            title = R.string.warning,
            bodyMessage = stringResource(R.string.delete_row_message),
            kindOfMessage = KindOfMessage.WARING,
            onConfirmation = {
                onEvents(AppointmentHistoryEvents.OnAccept)
            },
            isTwoButtonsAlert = IsTwoButtonsAlert(
                clickOnCancel = {
                    onEvents(AppointmentHistoryEvents.OnCancel)
                }
            )
        )
    )
}

@Composable
private fun AppointHistoryList(
    modifier: Modifier = Modifier,
    appointments: List<Appointment>,
    onEvents: (event: AppointmentHistoryEvents) -> Unit
) {
    Column(modifier.fillMaxSize()) {
        appointments.let { appointments ->
            if (appointments.isEmpty()) {
                NotDatView()
            } else {
                LazyColumn {
                    appointments.forEach { appointment ->
                        item {
                            ItemAppointment(
                                appointment = appointment,
                                onRemoveAppointment = {
                                    onEvents(AppointmentHistoryEvents.OnRemove(appointment.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemAppointment(
    appointment: Appointment,
    onRemoveAppointment: () -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                painter = painterResource(id = R.drawable.tienda),
                contentDescription = "ImageAppointment"
            )
            Text(text = appointment.service, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = appointment.branch, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable {
                    onRemoveAppointment.invoke()
                },
                color = Color.Red.copy(alpha = .80f),
                text = "Remove",
                fontSize = 24.sp,
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun AppointHistoryListPreview() {
    val mAppointment = Appointment(
        branch = "establishment",
        service = "service",
        id = ""
    )
    AppointHistoryList(
        appointments = listOf(mAppointment, mAppointment),
        onEvents = {}
    )
}

@Composable
private fun NotDatView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            getRandomNoDataAnimation()
        )
    )
    Box(Modifier.fillMaxSize()) {
        LottieAnimation(
            modifier = Modifier
                .height(250.dp)
                .width(250.dp),
            iterations = LottieConstants.IterateForever,
            composition = composition,
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