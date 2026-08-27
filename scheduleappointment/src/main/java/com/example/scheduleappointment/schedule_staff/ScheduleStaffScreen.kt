package com.example.scheduleappointment.schedule_staff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.scheduleappointment.R
import com.example.scheduleappointment.mainflow.AppointmentFlowViewModel
import com.example.scheduleappointment.mainflow.AppointmentFlowUiState
import com.example.scheduleappointment.mainflow.ScheduleAppointmentEvents
import com.example.scheduleappointment.mainflow.ScheduleAppointmentsSideEffects
import com.example.core.navigation.schedule.ScheduleNavigationRoutes
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.BackgroundListsMainFlow
import com.example.core.ui.theme.CardBorder
import com.example.core.ui.theme.Purple
import com.example.core.ui.theme.PurpleBorder
import com.example.core.ui.theme.PurpleLight
import com.example.core.ui.theme.StarColor
import com.example.core.ui.theme.TextMuted
import com.example.core.ui.theme.TextPrimary
import com.example.core.ui.theme.avatarBackgrounds
import com.example.domain.entities.remote.migration.Staff
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues


@Composable
fun ScheduleStaffScreen(
    navController: NavController,
    mainViewModel: AppointmentFlowViewModel
) {
    val uiState = mainViewModel.staffUiState.collectAsStateWithLifecycle()
    LaunchedEffect(mainViewModel) {
        mainViewModel.branchSideEffects.collectLatest { effect ->
            when (effect) {
                is ScheduleAppointmentsSideEffects.GoToDetailStaffScreen -> {
                    navController.navigate(ScheduleNavigationRoutes.DetailStaffRoute)
                }

                is ScheduleAppointmentsSideEffects.GoToScheduleService -> {
                    navController.navigate(ScheduleNavigationRoutes.ServicesRoute)
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
private fun ScheduleStaffScreenContent(
    modifier: Modifier = Modifier,
    uiState: AppointmentFlowUiState,
    onEvents: (ScheduleAppointmentEvents) -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.branchName,
                fontSize = 32.sp,
                style = TextStyle(fontWeight = FontWeight.W900)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "${uiState.listOfStaffs.size} estilistas",
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        SurpriseMeButton(
            onClick = {
                onEvents(ScheduleAppointmentEvents.OnRandomStaff)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(32.dp))
        ListStaffs(
            listOfStaffs = uiState.listOfStaffs,
            onEvents = onEvents
        )
    }
}

@Composable
private fun ListStaffs(
    listOfStaffs: List<Staff>,
    onEvents: (ScheduleAppointmentEvents) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(listOfStaffs) { myStaff ->
                StaffCard(
                    staff = myStaff,
                    colorIndex = listOfStaffs.indexOf(myStaff) % avatarBackgrounds.size,
                    onDetailClick = {
                        onEvents(ScheduleAppointmentEvents.ClickOnImageStaff(myStaff))
                    },
                    onSelectClick = {
                        onEvents(ScheduleAppointmentEvents.ClickOnStaff(myStaff))
                    }
                )
            }
        }

    }
}

@Composable
private fun StaffCard(
    staff: Staff,
    colorIndex: Int,
    onDetailClick: () -> Unit,
    onSelectClick: () -> Unit
) {
    val (bgColor, initialsColor) = avatarBackgrounds[colorIndex]

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(0.5.dp, CardBorder)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(initialsColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = staff.initials,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Surface(
                    onClick = onDetailClick,
                    shape = RoundedCornerShape(6.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    border = BorderStroke(0.5.dp, CardBorder),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.detail),
                        fontSize = 11.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = staff.name ?: "",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_list_24),
                        contentDescription = null,
                        tint = StarColor,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = staff.rating.toString(),
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Button(
                    onClick = onSelectClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurpleLight,
                        contentColor = Purple
                    ),
                    contentPadding = PaddingValues(0.dp),
                    border = BorderStroke(0.5.dp, PurpleBorder)
                ) {
                    Text(
                        text = stringResource(R.string.select),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun SurpriseMeButton(
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = BackgroundListsMainFlow,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_list_24),
                    contentDescription = null,
                    tint = TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.surprise_me),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(R.string.pick_random_stylist),
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_list_24),
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ScheduleStaffScreenContentPreview() {
    ScheduleStaffScreenContent(
        uiState = AppointmentFlowUiState(
            listOfStaffs = Staff.mockStaffList(),
            branchName = "Zacatecas"
        ),
        onEvents = {}
    )
}

