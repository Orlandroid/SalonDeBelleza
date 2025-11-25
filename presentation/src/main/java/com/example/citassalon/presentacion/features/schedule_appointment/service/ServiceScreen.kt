package com.example.citassalon.presentacion.features.schedule_appointment.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.schedule_appointment.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentEvents
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.schedule_appointment.staff.StaffUiState
import com.example.citassalon.presentacion.features.theme.BackgroundListsMainFlow
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff

@Composable
fun ServiceScreen(
    navController: NavController,
    mainViewModel: FlowMainViewModel,
    state: StaffUiState,
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_servicio))
    ) {
        ServiceScreenContent(
            modifier = Modifier,
            staff = state.currentStaff ?: Staff.mockStaff(),
            branch = state.branchName,
            listOfServices = mainViewModel.listOfServices,
            navigateToDateScreen = {
                navController.navigate(ScheduleAppointmentScreens.ScheduleRoute)
            },
            onEvents = { events -> mainViewModel.onEvents(events) }
        )
    }
}

@Composable
fun ServiceScreenContent(
    modifier: Modifier = Modifier,
    staff: Staff,
    branch: String,
    listOfServices: List<Service>,
    navigateToDateScreen: () -> Unit,
    onEvents: (ScheduleAppointmentEvents) -> Unit
) {
    Column(modifier.fillMaxSize()) {
        MediumSpacer(orientation = Orientation.VERTICAL)
        ItemStaff(
            staff = staff,
            branch = branch
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        Card(
            colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
        ) {
            ListServices(
                listOfServices = listOfServices,
                clickOnItem = {
                    navigateToDateScreen()
                    onEvents(ScheduleAppointmentEvents.ClickOnService(it))
                }
            )
        }
    }
}

@Composable
fun ListServices(
    modifier: Modifier = Modifier,
    listOfServices: List<Service>,
    clickOnItem: (service: Service) -> Unit
) {
    LazyColumn(modifier = modifier) {
        listOfServices.forEach { service ->
            item {
                TextWithArrow(
                    config = TextWithArrowConfig(
                        text = service.name,
                        clickOnItem = {
                            clickOnItem(service)
                        }
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ServiceScreenContentPreview() {
    ServiceScreenContent(
        staff = Staff.mockStaff(),
        branch = "Zacatecas",
        listOfServices = Service.mockListServices(),
        navigateToDateScreen = {},
        onEvents = {}
    )
}