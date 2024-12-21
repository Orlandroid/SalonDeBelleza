package com.example.citassalon.presentacion.features.schedule_appointment.staff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ClickOnItemStaff
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.schedule_appointment.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentEvents
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentsSideEffects
import com.example.citassalon.presentacion.features.theme.BackgroundListsMainFlow
import com.example.domain.entities.remote.migration.Staff

@Composable
fun ScheduleStaffScreen(
    navController: NavController,
    mainViewModel: FlowMainViewModel
) {
    val uiState = mainViewModel.staffUiState.collectAsStateWithLifecycle()
    LaunchedEffect(mainViewModel) {
        mainViewModel.branchSideEffects.collect { effect ->
            when (effect) {
                is ScheduleAppointmentsSideEffects.GoToDetailStaffScreen -> {
                    navController.navigate(ScheduleAppointmentScreens.DetailStaffRoute)
                }

                is ScheduleAppointmentsSideEffects.GoToScheduleService -> {
                    navController.navigate(ScheduleAppointmentScreens.ServicesRoute)
                }

                else -> {

                }
            }
        }
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_staff))
    ) {
        ScheduleStaffScreenContent(
            uiState = uiState.value,
            onEvents = { event ->
                mainViewModel.onEvents(event)
            }
        )
    }
}

@Composable
fun ScheduleStaffScreenContent(
    modifier: Modifier = Modifier,
    uiState: StaffUiState,
    onEvents: (ScheduleAppointmentEvents) -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = uiState.branchName,
            fontSize = 32.sp,
            style = TextStyle(fontWeight = FontWeight.W900)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonRandomStaff {
            onEvents(ScheduleAppointmentEvents.OnRandomStaff)
        }
        Spacer(modifier = Modifier.height(32.dp))
        ListStaffs(
            listOfStaffs = uiState.listOfStaffs,
            onEvents = onEvents
        )
    }
}

@Composable
fun ListStaffs(
    listOfStaffs: List<Staff>,
    onEvents: (ScheduleAppointmentEvents) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            listOfStaffs.forEach { myStaff ->
                item {
                    ItemStaff(
                        staff = myStaff,
                        onClick = {
                            when (it) {
                                ClickOnItemStaff.ClickOnImage -> {
                                    onEvents(ScheduleAppointmentEvents.ClickOnImageStaff(myStaff))
                                }

                                ClickOnItemStaff.ClickOnItem -> {
                                    onEvents(ScheduleAppointmentEvents.ClickOnStaff(myStaff))
                                }
                            }
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun ButtonRandomStaff(onClick: () -> Unit) {
    Button(onClick = {
        onClick.invoke()
    }) {
        Text(text = stringResource(id = R.string.estilista_aleatorio))
    }
}

@Composable
@Preview(showBackground = true)
fun ScheduleStaffScreenContentPreview(modifier: Modifier = Modifier) {
    ScheduleStaffScreenContent(
        uiState = StaffUiState(
            listOfStaffs = Staff.mockStaffList(),
            branchName = "Branch"
        ),
        onEvents = {}
    )
}

