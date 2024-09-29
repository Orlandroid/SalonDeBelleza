package com.example.citassalon.presentacion.features.schedule_appointment.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.theme.BackgroundListsMainFlow
import com.example.domain.entities.remote.migration.Service

@Composable
fun ServiceScreen(
    navController: NavController,
    mainViewModel: FlowMainViewModel
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_servicio))
    ) {
        ServiceScreenContent(modifier = Modifier, mainViewModel = mainViewModel) {
            navController.navigate(ScheduleAppointmentScreens.ScheduleRoute)
        }
    }
}

@Composable
fun ServiceScreenContent(
    modifier: Modifier = Modifier,
    mainViewModel: FlowMainViewModel,
    navigateToDateScreen: () -> Unit
) {
    Column(modifier.fillMaxSize()) {
        MediumSpacer(orientation = Orientation.VERTICAL)
        ItemStaff(
            staff = mainViewModel.currentStaff,
            branch = mainViewModel.sucursal.name
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        Card(
            colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
        ) {
            ListServices(
                listOfServices = mainViewModel.listOfServices,
                navigateToDateScreen = navigateToDateScreen
            )
        }
    }
}

@Composable
fun ListServices(
    modifier: Modifier = Modifier,
    listOfServices: List<Service>,
    navigateToDateScreen: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        listOfServices.forEach { service ->
            item {
                TextWithArrow(
                    config = TextWithArrowConfig(
                        text = service.name,
                        clickOnItem = {
                            navigateToDateScreen.invoke()
                        }
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ServiceScreenContentPreview(modifier: Modifier = Modifier) {
    ServiceScreenContent(mainViewModel = FlowMainViewModel()) {}
}