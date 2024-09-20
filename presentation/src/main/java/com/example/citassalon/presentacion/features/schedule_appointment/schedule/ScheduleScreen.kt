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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.extensions.dateFormat
import com.example.citassalon.presentacion.features.extensions.getCurrentDateTime
import com.example.citassalon.presentacion.features.extensions.getHourFormat
import com.example.citassalon.presentacion.features.extensions.getInitialTime
import com.example.citassalon.presentacion.features.extensions.timeFormat
import com.example.citassalon.presentacion.features.extensions.toStringFormat
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun ScheduleScreen(
    navController: NavController,
    flowMainViewModel: FlowMainViewModel
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_hora))
    ) {
        ScheduleScreenContent(flowMainViewModel = flowMainViewModel) {
            navController.navigate(ScheduleAppointmentScreens.ScheduleConfirmation.route)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreenContent(
    modifier: Modifier = Modifier,
    flowMainViewModel: FlowMainViewModel,
    goToConfirmationScreen: () -> Unit
) {
    val date = remember { mutableStateOf(getCurrentDateTime().toStringFormat(dateFormat)) }
    val myTime = remember { mutableStateOf(getInitialTime()) }
    val showDatePickerDialog = remember { mutableStateOf(false) }
    val showTimePickerDialog = remember { mutableStateOf(false) }
    flowMainViewModel.hourAppointment = myTime.value
    flowMainViewModel.dateAppointment = date.value
    if (showTimePickerDialog.value) {
        MyTimePickerDialog(
            onDismiss = {
                showTimePickerDialog.value = false
            },
            onConfirm = {
                showTimePickerDialog.value = false
                myTime.value = it.getHourFormat()
            }
        )
    }
    if (showDatePickerDialog.value) {
        MyDatePickerDialog(
            onDismiss = {
                showDatePickerDialog.value = false
            },
            onDateSelected = {
                date.value = it
            }
        )
    }
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val (cardInfoStaff, cardTime) = createRefs()
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.constrainAs(cardInfoStaff) {
                top.linkTo(parent.top, 32.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            StaffInfo(flowMainViewModel = flowMainViewModel)
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
                InputDate(currentDate = date.value) {
                    showDatePickerDialog.value = true
                }
                InputTime(currentTime = myTime.value) {
                    showTimePickerDialog.value = true
                }
                NextButton {
                    goToConfirmationScreen.invoke()
                }
            }

        }
    }
}


@Composable
fun StaffInfo(flowMainViewModel: FlowMainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = flowMainViewModel.currentStaff.image_url,
            contentDescription = "imageStaff",
            modifier = Modifier
                .height(150.dp)
                .size(150.dp)
                .padding(top = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            fontSize = 30.sp, text = flowMainViewModel.currentStaff.nombre
        )
        Text(
            fontSize = 30.sp, text = flowMainViewModel.sucursal.name
        )
        Text(
            fontSize = 30.sp, text = flowMainViewModel.listOfServices[0].name
        )
        Text(
            fontSize = 30.sp,
            text = flowMainViewModel.listOfServices[0].precio.toString(),
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}


@Composable
fun InputDate(
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
            Icon(imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.clickable {
                    clickOnIcon.invoke()
                }
            )
        }
    )
}

@Composable
fun InputTime(
    currentTime: String,
    clickOnIconTime: () -> Unit
) {
    TextField(modifier = Modifier.clickable { },
        enabled = false,
        value = currentTime,
        onValueChange = {

        },
        placeholder = { Text(stringResource(id = R.string.selecciona_la_hora_de_tu_cita)) },
        trailingIcon = {
            Icon(imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                modifier = Modifier.clickable {
                    clickOnIconTime()
                }
            )
        }
    )
}

@Composable
fun NextButton(goToConfirmationScreen: () -> Unit) {
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
fun ScheduleScreenContentPreview(modifier: Modifier = Modifier) {
    ScheduleScreenContent(
        flowMainViewModel = FlowMainViewModel(),
        goToConfirmationScreen = {}
    )
}