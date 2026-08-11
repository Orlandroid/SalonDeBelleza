package com.example.profile.historial_citas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.profile.ProfileNavigationRoutes
import com.example.profile.R
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.dialogs.AlertDialogMessagesConfig
import com.example.core.ui.dialogs.BaseAlertDialogMessages
import com.example.core.ui.dialogs.IsTwoButtonsAlert
import com.example.core.ui.dialogs.KindOfMessage
import com.example.core.ui.dialogs.ProgressDialog
import com.example.domain.perfil.Appointment
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppointmentHistoryScreen(
    navController: NavHostController,
    viewModel: AppointmentHistoryViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest {
            when (it) {
                is AppointmentHistoryEffects.NavigateToDetail -> {
                    navController.navigate(
                        route = ProfileNavigationRoutes.HistoryDetailRoute(appointmentId = it.idAppointment)
                    )
                }
            }
        }
    }
    when {
        uiState.value.isLoading -> {
            ProgressDialog()
        }

        uiState.value.error != null -> {
            BaseErrorScreen()
        }

        else -> {
            AppointmentHistoryScreenContent(
                uiState = uiState.value,
                onEvents = viewModel::onEvents,
                navHostController = navController
            )
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
            isTwoButtonsAlert = IsTwoButtonsAlert(
                clickOnCancel = {
                    onEvents(AppointmentHistoryEvents.OnCancel)
                },
                clickOnAccept = {
                    onEvents(AppointmentHistoryEvents.OnAccept)
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        if (appointments.isEmpty()) {
            NotDatView()
        } else {
            Appointments(
                appointments = appointments,
                onEvents = onEvents
            )
        }
    }
}

@Composable
private fun Appointments(
    appointments: List<Appointment>,
    onEvents: (event: AppointmentHistoryEvents) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = appointments,
            key = { it.id }
        ) { appointment ->
            ItemAppointment(
                appointment = appointment,
                onAppointmentClicked = { appointmentId ->
                    onEvents(
                        AppointmentHistoryEvents.OnAppointmentClicked(
                            appointmentId
                        )
                    )
                },
                onRemoveAppointment = {
                    onEvents(AppointmentHistoryEvents.OnRemove(appointment.id))
                }
            )
        }
    }
}

@Composable
private fun ItemAppointment(
    appointment: Appointment,
    onRemoveAppointment: () -> Unit,
    onAppointmentClicked: (appointmentId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = { onAppointmentClicked(appointment.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                Image(
                    modifier = Modifier.size(72.dp),
                    painter = painterResource(id = R.drawable.tienda),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp))


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = appointment.service,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                BranchRow(branchName = appointment.branch)
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(onClick = onRemoveAppointment) {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    contentDescription = stringResource(R.string.remove),
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
private fun BranchRow(branchName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.Storefront,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = branchName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun NotDatView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            getRandomNoDataAnimation()
        )
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieAnimation(
                modifier = Modifier
                    .height(220.dp)
                    .width(220.dp),
                iterations = LottieConstants.IterateForever,
                composition = composition,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.no_appointments_yet),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun getRandomNoDataAnimation(): Int = when ((1..3).random()) {
    1 -> R.raw.no_data_animation
    2 -> R.raw.no_data_available
    else -> R.raw.no_data_found
}

@Composable
@Preview(showBackground = true)
private fun AppointHistoryListPreview() {
    val mAppointment = Appointment(
        branch = "Sucursal Centro",
        service = "Delineado de barba y bigote, o cejas",
        id = "1"
    )
    AppointHistoryList(
        appointments = listOf(
            mAppointment,
            mAppointment.copy(id = "2"),
            mAppointment.copy(id = "3")
        ),
        onEvents = {}
    )
}