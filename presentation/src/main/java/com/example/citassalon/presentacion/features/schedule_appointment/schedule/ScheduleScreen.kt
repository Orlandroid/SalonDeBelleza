package com.example.citassalon.presentacion.features.schedule_appointment.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.extensions.getHourFormat
import com.example.citassalon.presentacion.features.schedule_appointment.mainflow.AppointmentFlowViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.schedule_appointment.mainflow.AppointmentFlowUiState
import com.example.citassalon.presentacion.features.schedule_appointment.mainflow.ScheduleAppointmentEvents
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    navController: NavController,
    flowMainViewModel: AppointmentFlowViewModel,
    scheduleScreenViewmodel: ScheduleScreenViewmodel = hiltViewModel()
) {
    val uiState by scheduleScreenViewmodel.uiState.collectAsStateWithLifecycle()
    val state = flowMainViewModel.staffUiState.collectAsStateWithLifecycle()
    val onEvents = scheduleScreenViewmodel::onEvents
    LaunchedEffect(Unit) {
        scheduleScreenViewmodel.effects.collectLatest {
            when (it) {
                ScheduleScreenEffects.NavigateToConfirmationScreen -> {
                    navController.navigate(ScheduleAppointmentScreens.ScheduleConfirmationRoute)
                }
            }
        }
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_hora))
    ) {
        if (uiState.showTimeDialog) {
            MyTimePickerDialog(
                onDismiss = {
                    onEvents(ScheduleScreenEvents.OnDismissTime)
                }, onConfirm = {
                    val time = it.getHourFormat()
                    onEvents(ScheduleScreenEvents.OnConfirmTime(time = time))
                    flowMainViewModel.onEvents(ScheduleAppointmentEvents.TimeSelected(time = time))
                }
            )
        }
        if (uiState.showDateDialog) {
            MyDatePickerDialog(
                onDismiss = {
                    onEvents(ScheduleScreenEvents.OnDismissDate)
                },
                onDateSelected = { dateSelected ->
                    onEvents(ScheduleScreenEvents.OnConfirmDate(dateSelected))
                    flowMainViewModel.onEvents(ScheduleAppointmentEvents.DateSelected(dateSelected))
                }
            )
        }
        ScheduleScreenContent(
            date = uiState.dateAppointment,
            time = uiState.hourAppointment,
            onEvents = onEvents,
            state = state.value,
        )
    }
}

@Composable
private fun ScheduleScreenContent(
    modifier: Modifier = Modifier,
    state: AppointmentFlowUiState,
    date: String,
    time: String,
    onEvents: (event: ScheduleScreenEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        StaffInfo(
            image = state.currentStaff?.image_url.orEmpty(),
            name = state.currentStaff?.name.orEmpty(),
            branch = state.branchName,
            services = state.listOfServices[0].name,
            price = state.listOfServices[0].precio.toString()
        )
        Spacer(modifier = Modifier.height(24.dp))
        ScheduleInputs(date = date, time = time, onEvents = onEvents)
    }
}


@Composable
private fun StaffInfo(
    image: String,
    name: String,
    branch: String,
    services: String,
    price: String
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "Foto de $name",
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow(icon = Icons.Default.Place, text = branch)
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow(icon = Icons.Default.ContentCut, text = services)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$$price",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
private fun ScheduleInputs(
    date: String,
    time: String,
    onEvents: (event: ScheduleScreenEvents) -> Unit
) {
    OutlinedCard(
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputDate(currentDate = date) {
                onEvents(ScheduleScreenEvents.OnDateClicked)
            }
            InputTime(currentTime = time) {
                onEvents(ScheduleScreenEvents.OnTimeClicked)
            }
            Spacer(modifier = Modifier.height(4.dp))
            NextButton(enabled = date.isNotBlank() && time.isNotBlank()) {
                onEvents(ScheduleScreenEvents.OnNextButtonClicked)
            }
        }
    }
}

@Composable
private fun InputDate(
    currentDate: String,
    clickOnIcon: () -> Unit
) {
    OutlinedTextField(
        enabled = false,
        readOnly = true,
        value = currentDate,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .clickable { clickOnIcon() },
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(stringResource(id = R.string.add_date)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.clickable { clickOnIcon() }
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledTrailingIconColor = MaterialTheme.colorScheme.primary,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Composable
private fun InputTime(
    currentTime: String,
    clickOnIconTime: () -> Unit
) {
    OutlinedTextField(
        enabled = false,
        readOnly = true,
        value = currentTime,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .clickable { clickOnIconTime() },
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(stringResource(id = R.string.selecciona_la_hora_de_tu_cita)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                modifier = Modifier.clickable { clickOnIconTime() }
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledTrailingIconColor = MaterialTheme.colorScheme.primary,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Composable
private fun NextButton(
    enabled: Boolean,
    goToConfirmationScreen: () -> Unit
) {
    Button(
        onClick = { goToConfirmationScreen() },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors()
    ) {
        Text(
            text = stringResource(id = R.string.next),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun ScheduleScreenContentPreview() {
    ScheduleScreenContent(
        date = "12/07/2024",
        time = "15:42",
        state = AppointmentFlowUiState(
            branchName = "Zacatecas",
            currentStaff = Staff(
                id = "",
                name = "Orlando",
                gender = "h",
                image_url = "",
                rating = 4
            ),
            listOfServices = listOf(Service(id = "", name = "Uñas", precio = 200))
        ),
        onEvents = {}
    )
}