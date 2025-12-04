package com.example.citassalon.presentacion.features.schedule_appointment.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.extensions.getHourFormat
import com.example.citassalon.presentacion.features.schedule_appointment.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.staff.StaffUiState
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    navController: NavController,
    flowMainViewModel: FlowMainViewModel,
    scheduleScreenViewmodel: ScheduleScreenViewmodel = hiltViewModel()
) {
    val uiState by scheduleScreenViewmodel.uiState.collectAsStateWithLifecycle()
    flowMainViewModel.hourAppointment = uiState.hourAppointment
    flowMainViewModel.dateAppointment = uiState.dateAppointment
    val state = flowMainViewModel.staffUiState.collectAsStateWithLifecycle()
    val onEvents = scheduleScreenViewmodel::onEvents
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_hora))
    ) {
        if (uiState.showTimeDialog) {
            MyTimePickerDialog(
                onDismiss = {
                    onEvents(ScheduleScreenEvents.OnDismissTime)
                }, onConfirm = {
                    onEvents(ScheduleScreenEvents.OnConfirmTime(time = it.getHourFormat()))
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
    state: StaffUiState,
    date: String,
    time: String,
    onEvents: (event: ScheduleScreenEvents) -> Unit
) {
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val (cardInfoStaff, cardTime) = createRefs()
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), shape = MaterialTheme.shapes.medium, modifier = Modifier.constrainAs(cardInfoStaff) {
                top.linkTo(parent.top, 32.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            }) {
            StaffInfo(
                image = state.currentStaff?.image_url.orEmpty(),
                name = state.currentStaff?.nombre.orEmpty(),
                branch = state.branchName,
                services = state.listOfServices[0].name,
                price = state.listOfServices[0].precio.toString()
            )
        }
        OutlinedCard(
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier.constrainAs(cardTime) {
                top.linkTo(cardInfoStaff.bottom, 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputDate(currentDate = date) {
                    onEvents(ScheduleScreenEvents.OnDateClicked)
                }
                InputTime(currentTime = time) {
                    onEvents(ScheduleScreenEvents.OnTimeClicked)
                }
                NextButton {
                    onEvents(ScheduleScreenEvents.OnNextButtonClicked)
                }
            }

        }
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = image,
            contentDescription = "imageStaff",
            modifier = Modifier
                .height(150.dp)
                .size(150.dp)
                .padding(top = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            fontSize = 30.sp,
            text = name
        )
        Text(
            fontSize = 30.sp,
            text = branch
        )
        Text(
            fontSize = 30.sp,
            text = services
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 30.sp,
            text = price
        )
    }
}


@Composable
private fun InputDate(
    currentDate: String,
    clickOnIcon: () -> Unit
) {
    TextField(
        enabled = false,
        value = currentDate,
        onValueChange = {

        },
        placeholder = {
            Text(stringResource(id = R.string.add_date))
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.clickable {
                    clickOnIcon.invoke()
                }
            )
        }
    )
}

@Composable
private fun InputTime(
    currentTime: String, clickOnIconTime: () -> Unit
) {
    TextField(
        modifier = Modifier.clickable { },
        enabled = false,
        value = currentTime,
        onValueChange = {

        },
        placeholder = { Text(stringResource(id = R.string.selecciona_la_hora_de_tu_cita)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                modifier = Modifier.clickable {
                    clickOnIconTime()
                }
            )
        }
    )
}

@Composable
private fun NextButton(
    goToConfirmationScreen: () -> Unit
) {
    Button(
        onClick = {
            goToConfirmationScreen.invoke()
        }
    ) {
        Text(text = stringResource(id = R.string.next))
    }
}

@Composable
@Preview(showBackground = true)
private fun ScheduleScreenContentPreview() {
    ScheduleScreenContent(
        date = "12/07/2024",
        time = "15:42",
        state = StaffUiState(
            currentStaff = Staff(
                id = "",
                nombre = "Orlando",
                sexo = "h",
                image_url = "",
                valoracion = 4
            ),
            listOfServices = listOf(Service(id = "", name = "Uñas", precio = 200))
        ),
        onEvents = {}
    )
}