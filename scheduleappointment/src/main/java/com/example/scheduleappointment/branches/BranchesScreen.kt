package com.example.scheduleappointment.branches

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.scheduleappointment.R
import com.example.scheduleappointment.mainflow.AppointmentFlowViewModel
import com.example.scheduleappointment.mainflow.ScheduleAppointmentEvents
import com.example.scheduleappointment.mainflow.ScheduleAppointmentsSideEffects
import com.example.core.navigation.info.InfoNavigationScreens
import com.example.core.navigation.schedule.ScheduleNavigationRoutes
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.BaseScreenState
import com.example.core.ui.base.LongSpacer
import com.example.core.ui.base.Orientation
import com.example.core.ui.base.getContentOrNull
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.TextWithArrowConfig
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.components.skeletons.BranchesScreenSkeletons
import com.example.core.ui.theme.Background
import com.example.core.ui.theme.BackgroundListsMainFlow
import com.example.core.ui.theme.GreenOpen
import com.example.core.ui.theme.RedClosed
import com.example.domain.entities.remote.migration.NegoInfo

@Composable
fun BranchesScreen(
    navController: NavController,
    mainViewModel: AppointmentFlowViewModel,
    branchViewModel: BranchViewModel = hiltViewModel()
) {
    val uiState by branchViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(mainViewModel) {
        mainViewModel.branchSideEffects.collect { effect ->
            when (effect) {
                ScheduleAppointmentsSideEffects.GoToScheduleStaff -> {
                    navController.navigate(ScheduleNavigationRoutes.ScheduleStaffRoute)
                }

                ScheduleAppointmentsSideEffects.GotoBranchInfo -> {
                    navController.navigate(InfoNavigationScreens.BranchInfoRoute)
                }

                else -> {}
            }
        }
    }
    when (uiState) {
        BaseScreenState.OnLoading -> {
            BranchesScreenSkeletons()
        }

        is BaseScreenState.OnContent -> {
            uiState.getContentOrNull()?.let { state ->
                BaseComposeScreen(
                    toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_sucursal)),
                    navController = navController
                ) {
                    BranchesScreenContent(
                        modifier = Modifier,
                        branches = state,
                        onEvents = { event ->
                            mainViewModel.onEvents(event)
                        }
                    )
                }
            }
        }

        is BaseScreenState.OnError -> {
            BaseErrorScreen()
        }
    }
}

@Composable
private fun BranchesScreenContent(
    modifier: Modifier = Modifier,
    branches: List<NegoInfo>?,
    onEvents: (ScheduleAppointmentEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.avalible_locations),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "${branches?.size ?: 0} Branches",
                fontSize = 20.sp
            )
        }
        branches?.let {
            Branches(
                branches = branches,
                currentBranch = { chosenBranch ->
                    onEvents(
                        ScheduleAppointmentEvents.ClickOnBranch(
                            branch = chosenBranch
                        )
                    )
                }
            )
        }
        LongSpacer(orientation = Orientation.VERTICAL)

    }
}

@Composable
private fun Branches(
    branches: List<NegoInfo>,
    currentBranch: (NegoInfo) -> Unit
) {
    LazyColumn {
        branches.forEach { branch ->
            item {
                BranchItem(
                    config =
                        TextWithArrowConfig(
                            text = branch.sucursal.name,
                            clickOnItem = {
                                currentBranch(branch)
                            }
                        ),
                    isOpen = branch.sucursal.isOpen
                )
            }
        }
    }
}

@Composable
private fun OpenBadge(isOpen: Boolean) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (isOpen) GreenOpen.copy(alpha = 0.12f) else RedClosed.copy(alpha = 0.12f)
    ) {
        Text(
            text = if (isOpen) stringResource(R.string.open) else stringResource(R.string.closed),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isOpen) GreenOpen else RedClosed,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun BranchItem(
    config: TextWithArrowConfig,
    isOpen: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow),
        elevation = CardDefaults.cardElevation(config.cardElevation),
        shape = RoundedCornerShape(config.cornerSize),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black
        ),
        onClick = {
            config.clickOnItem.invoke()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = config.verticalPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = config.text,
                    fontSize = config.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    modifier = Modifier.padding(end = 16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                    contentDescription = null,
                    tint = Color.Black,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Main Street",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                OpenBadge(isOpen = isOpen)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun BranchesScreenContentPreview() {
    BranchesScreenContent(
        modifier = Modifier,
        branches = NegoInfo.mockBusinessList(),
        onEvents = {}
    )
}

