package com.example.admin.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.admin.R
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.domain.AdminAppointmentUiModel

@Composable
fun AdminDashboardRoute(
    navController: NavController,
    viewModel: AdminViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration =
            ToolbarConfiguration(
                title = stringResource(R.string.admin_dashboard),
                isWithBackIcon = true,
            ),
        isLoading = uiState.isLoading,
    ) {
        AdminDashboardScreenContent(uiState = uiState, onEvents = viewModel::onEvents)
    }
}

@Composable
private fun AdminDashboardScreenContent(
    uiState: AdminUiState,
    onEvents: (AdminEvents) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = stringResource(R.string.business_overview),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            ContainerCards {
                MetricCard(
                    title = stringResource(R.string.revenue),
                    value = "$${uiState.metrics.totalRevenue}",
                    modifier = Modifier.weight(1f),
                )
                MetricCard(
                    title = stringResource(R.string.bookings),
                    value = "${uiState.metrics.totalBookings}",
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ContainerCards {
                MetricCard(
                    title = stringResource(R.string.active_clients),
                    value = "${uiState.metrics.activeClientsCount}",
                    modifier = Modifier.weight(1f),
                )
                MetricCard(
                    title = stringResource(R.string.pending_approval),
                    value = "${uiState.metrics.pendingAppointmentsCount}",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.pending_appointments),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        if (uiState.pendingAppointments.isEmpty()) {
            item {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = stringResource(R.string.no_pending_appointments))
                }
            }
        } else {
            items(uiState.pendingAppointments) { appointment ->
                PendingAppointmentCard(
                    appointment = appointment,
                    onApprove = {
                        onEvents(AdminEvents.OnApprove(appointmentId = appointment.id))
                    },
                    onReject = {
                        onEvents(AdminEvents.OnReject(appointmentId = appointment.id))
                    },
                )
            }
        }
    }
}

@Composable
private fun ContainerCards(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        content()
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = title, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PendingAppointmentCard(
    appointment: AdminAppointmentUiModel,
    onApprove: () -> Unit,
    onReject: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = appointment.serviceName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text =
                    stringResource(
                        R.string.client,
                        appointment.clientName,
                    ),
            )
            Text(
                text =
                    stringResource(
                        R.string.staff,
                        appointment.staffName,
                    ),
            )

            Text(
                text =
                    stringResource(
                        R.string.date_and_time,
                        appointment.date,
                        appointment.time,
                    ),
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                RejectButton(onReject = onReject)
                Spacer(modifier = Modifier.width(8.dp))
                ConfirmButton(onApprove = onApprove)
            }
        }
    }
}

@Composable
private fun ConfirmButton(onApprove: () -> Unit) {
    Button(onClick = onApprove) {
        Icon(
            Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(stringResource(R.string.confirm))
    }
}

@Composable
private fun RejectButton(onReject: () -> Unit) {
    OutlinedButton(
        onClick = onReject,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(R.string.reject))
    }
}
