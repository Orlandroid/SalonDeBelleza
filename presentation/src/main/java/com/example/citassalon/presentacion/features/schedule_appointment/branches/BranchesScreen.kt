package com.example.citassalon.presentacion.features.schedule_appointment.branches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreenState
import com.example.citassalon.presentacion.features.base.LongSpacer
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.share_beetwen_sucursales.BranchViewModel
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.features.theme.BackgroundListsMainFlow
import com.example.domain.entities.remote.migration.NegoInfo

@Composable
fun BranchesScreen(
    navController: NavController,
    mainViewModel: FlowMainViewModel,
    branchViewModel: BranchViewModel = hiltViewModel(),
    flow: Flow,
) {
    val state = branchViewModel.state.collectAsStateWithLifecycle()
    BaseComposeScreenState(
        alertDialogMessagesConfig = AlertDialogMessagesConfig(
            onConfirmation = {

            }
        ),
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_sucursal)),
        navController = navController,
        state = state.value
    ) {
        BranchesScreenContent(
            modifier = Modifier,
            branches = (state.value as BaseScreenState.Success<List<NegoInfo>>).data,
            mainViewModel = mainViewModel,
        ) {
            when (flow) {
                Flow.SCHEDULE_APPOINTMENT -> {
                    navController.navigate(ScheduleAppointmentScreens.ScheduleStaffRoute)
                }

                Flow.INFO -> {
                    navController.navigate(InfoNavigationScreens.BranchInfoRoute)
                }
            }
        }
    }
}

@Composable
fun BranchesScreenContent(
    modifier: Modifier = Modifier,
    branches: List<NegoInfo>?,
    mainViewModel: FlowMainViewModel,
    goToNextScreen: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background), verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
        ) {
            ShowBranches(
                branches = branches,
                goToNextScreen = goToNextScreen,
                currentBranch = { chosenBranch ->
                    mainViewModel.let {
                        it.sucursal = chosenBranch.sucursal
                        it.listOfStaffs = chosenBranch.staffs
                        it.listOfServices = chosenBranch.services
                    }
                }
            )
            LongSpacer(orientation = Orientation.VERTICAL)
        }

    }
}

@Composable
private fun ShowBranches(
    branches: List<NegoInfo>?,
    goToNextScreen: () -> Unit,
    currentBranch: (NegoInfo) -> Unit
) {
    LazyColumn {
        branches?.forEach { branch ->
            item {
                MediumSpacer(orientation = Orientation.VERTICAL)
                TextWithArrow(
                    TextWithArrowConfig(text = branch.sucursal.name,
                        clickOnItem = {
                            goToNextScreen.invoke()
                            currentBranch.invoke(branch)
                        }
                    )
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun BranchesScreenContentPreview(modifier: Modifier = Modifier) {

}

