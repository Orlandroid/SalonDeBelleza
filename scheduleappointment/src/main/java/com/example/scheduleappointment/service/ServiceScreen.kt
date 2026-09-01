package com.example.scheduleappointment.service

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.core.navigation.schedule.ScheduleNavigationRoutes
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.MediumSpacer
import com.example.core.ui.base.Orientation
import com.example.core.ui.components.ItemStaff
import com.example.core.ui.components.TextWithArrowConfig
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.BackgroundListsMainFlow
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.scheduleappointment.R

@Composable
fun ServiceScreen(
    navController: NavController,
    viewmodel: ServiceViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState = viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewmodel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewmodel.effects.collect { effect ->
                when (effect) {
                    ServiceEffects.NavigateToScheduleAppointment -> {
                        navController.navigate(ScheduleNavigationRoutes.ScheduleRoute)
                    }
                }
            }
        }
    }

    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.agendar_servicio))
    ) {
        val staff = uiState.value.currentStaff
        if (staff == null) {
            ServiceScreenLoading(modifier = Modifier)
        } else {
            ServiceScreenContent(
                modifier = Modifier,
                staff = staff,
                branch = uiState.value.branchName.orEmpty(),
                listOfServices = uiState.value.services,
                clickOnService = { service ->
                    viewmodel.onEvents(
                        ServiceEvents.ClickOnService(
                            service
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun ServiceScreenLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ServiceScreenContent(
    modifier: Modifier = Modifier,
    staff: Staff,
    branch: String,
    listOfServices: List<Service>,
    clickOnService: (service: Service) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        MediumSpacer(orientation = Orientation.VERTICAL)
        ItemStaff(
            staff = staff,
            branch = branch
        )
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
        ) {
            ListServices(
                listOfServices = listOfServices,
                clickOnItem = clickOnService
            )
        }
    }
}

@Composable
fun ServiceListItem(
    config: TextWithArrowConfig
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
        onClick = config.clickOnItem
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = config.verticalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = config.text,
                fontSize = config.fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun ListServices(
    modifier: Modifier = Modifier,
    listOfServices: List<Service>,
    clickOnItem: (service: Service) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = listOfServices,
            key = { it.id }
        ) { service ->
            ServiceListItem(
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

@Composable
@Preview(showBackground = true)
private fun ServiceScreenContentPreview() {
    ServiceScreenContent(
        staff = Staff.mockStaff(),
        branch = "Zacatecas",
        listOfServices = Service.mockListServices(),
        clickOnService = {}
    )
}