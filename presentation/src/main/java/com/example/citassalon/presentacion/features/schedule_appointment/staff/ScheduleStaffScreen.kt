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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ClickOnItemStaff
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.theme.BackgroundListsMainFlow
import com.example.domain.entities.remote.migration.Staff

@Composable
fun ScheduleStaffScreen(
    navController: NavController,
    mainViewModel: FlowMainViewModel
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_staff))
    ) {
        ScheduleStaffScreenContent(
            branchName = mainViewModel.sucursal.name,
            listOfStaffs = mainViewModel.listOfStaffs,
            navigateToDetailScreen = {
                navController.navigate(ScheduleAppointmentScreens.DetailStaffRoute)
            },
            navigateToServicesScreen = {
                navController.navigate(ScheduleAppointmentScreens.ServicesRoute)
            },
            clickOnRandomStaff = {
                val randomStaff = mainViewModel.listOfStaffs.indices.random()
                mainViewModel.currentStaff = mainViewModel.listOfStaffs[randomStaff]
            },
            clickOnStaff = { chosenStaff ->
                mainViewModel.currentStaff = chosenStaff
            }
        )
    }
}

@Composable
fun ScheduleStaffScreenContent(
    modifier: Modifier = Modifier,
    listOfStaffs: List<Staff>,
    branchName: String,
    clickOnStaff: (Staff) -> Unit,
    clickOnRandomStaff: () -> Unit,
    navigateToDetailScreen: () -> Unit,
    navigateToServicesScreen: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = branchName,
            fontSize = 32.sp,
            style = TextStyle(fontWeight = FontWeight.W900)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonRandomStaff {
            clickOnRandomStaff.invoke()
            navigateToServicesScreen.invoke()
        }
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
        ) {
            ListStaffs(
                listOfStaffs = listOfStaffs,
                navigateToDetailScreen = navigateToDetailScreen,
                navigateToServicesScreen = navigateToServicesScreen,
                clickOnStaff = clickOnStaff
            )
        }
    }
}

@Composable
fun ListStaffs(
    listOfStaffs: List<Staff>,
    navigateToDetailScreen: () -> Unit,
    navigateToServicesScreen: () -> Unit,
    clickOnStaff: (Staff) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        listOfStaffs.forEach { myStaff ->
            item {
                ItemStaff(
                    staff = myStaff,
                    onClick = {
                        clickOnStaff.invoke(myStaff)
                        when (it) {
                            ClickOnItemStaff.ClickOnImage -> {
                                navigateToServicesScreen()
                            }

                            ClickOnItemStaff.ClickOnItem -> {
                                navigateToDetailScreen()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ButtonRandomStaff(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick.invoke()
        }
    ) {
        Text(text = stringResource(id = R.string.estilista_button))
    }
}

@Composable
@Preview(showBackground = true)
fun ScheduleStaffScreenContentPreview(modifier: Modifier = Modifier) {
    ScheduleStaffScreenContent(
        navigateToDetailScreen = {},
        navigateToServicesScreen = {},
        listOfStaffs = Staff.mockStaffList(),
        branchName = "Zacatecas",
        clickOnStaff = {},
        clickOnRandomStaff = {}
    )
}

