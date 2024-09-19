package com.example.citassalon.presentacion.features.schedule_appointment.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens

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
            navController.navigate(ScheduleAppointmentScreens.Schedule.route)
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
        Spacer(modifier = Modifier.height(16.dp))
        ItemStaff(
            staff = mainViewModel.currentStaff,
            branch = mainViewModel.sucursal.name
        )
        LazyColumn {
            mainViewModel.listOfServices.forEach { service ->
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
}

@Composable
@Preview(showBackground = true)
fun ServiceScreenContentPreview(modifier: Modifier = Modifier) {
    ServiceScreenContent(mainViewModel = FlowMainViewModel()) {}
}