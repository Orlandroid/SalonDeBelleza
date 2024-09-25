package com.example.citassalon.presentacion.features.schedule_appointment.branch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.share_beetwen_sucursales.BranchViewModel
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.state.ApiState

@Composable
fun BranchesScreen(
    navController: NavController,
    mainViewModel: FlowMainViewModel,
    branchViewModel: BranchViewModel = hiltViewModel()
) {
    val branchesState = branchViewModel.branches.observeAsState()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_sucursal))
    ) {
        BranchesScreenContent(
            modifier = Modifier,
            branches = branchesState,
            navController = navController,
            mainViewModel = mainViewModel,
        )
    }
}

@Composable
fun BranchesScreenContent(
    modifier: Modifier = Modifier,
    branches: State<ApiState<List<NegoInfo>>?>,
    navController: NavController,
    mainViewModel: FlowMainViewModel,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowBranches(branches = branches,
            navigateToBranchesScreen = {
                navController.navigate(ScheduleAppointmentScreens.ScheduleStaffRoute)
            },
            currentBranch = { chosenBranch ->
                mainViewModel.let {
                    it.sucursal = chosenBranch.sucursal
                    it.listOfStaffs = chosenBranch.staffs
                    it.listOfServices = chosenBranch.services
                }
            }
        )
    }
}

//TODO add shimmer effect to list
@Composable
private fun ShowBranches(
    branches: State<ApiState<List<NegoInfo>>?>,
    navigateToBranchesScreen: () -> Unit,
    currentBranch: (NegoInfo) -> Unit
) {
    when (branches.value) {
        is ApiState.Loading -> {
            ProgressDialog()
        }

        is ApiState.Success -> {
            LazyColumn {
                branches.value?.data?.forEach { branch ->
                    item {
                        TextWithArrow(
                            TextWithArrowConfig(text = branch.sucursal.name,
                                clickOnItem = {
                                    navigateToBranchesScreen.invoke()
                                    currentBranch.invoke(branch)
                                }
                            )
                        )
                    }
                }
            }
        }

        else -> {}
    }

}


@Composable
@Preview(showBackground = true)
fun BranchesScreenContentPreview(modifier: Modifier = Modifier) {

}

