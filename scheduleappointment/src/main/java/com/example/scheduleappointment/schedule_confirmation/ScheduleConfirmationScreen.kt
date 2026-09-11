package com.example.scheduleappointment.schedule_confirmation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.AlwaysBlack
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.ui.theme.Background
import com.example.core.util.toCurrencyString
import com.example.domain.loyalty.PromotionCode
import com.example.domain.wallet.Currency
import com.example.scheduleappointment.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ScheduleConfirmationScreen(
    navController: NavController,
    confirmScheduleViewModel: ConfirmScheduleViewModel = hiltViewModel(),
    navigateToAppointmentSchedule: () -> Unit
) {
    val uiState by confirmScheduleViewModel.uiState.collectAsStateWithLifecycle()
    val onEvents = confirmScheduleViewModel::onEvents
    LaunchedEffect(Unit) {
        confirmScheduleViewModel.effects.collectLatest {
            when (it) {
                ScheduleAppointmentEffects.NavigateToAppointComplete -> {
                    navigateToAppointmentSchedule.invoke()
                }
            }
        }
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.confirmar_cita))
    ) {
        ScheduleConfirmationScreenContent(
            uiState = uiState,
            event = onEvents
        )
    }
}

@Composable
private fun ScheduleConfirmationScreenContent(
    modifier: Modifier = Modifier,
    uiState: ScheduleAppointmentState,
    event: (ScheduleAppointmentEvents) -> Unit
) {
    if (uiState.showConfirmationDialog) {
        ConfirmAppointmentDialog(
            clickOnAccept = {
                event(ScheduleAppointmentEvents.OnConfirmationAppointmentAccepted)
            },
            clickOnCancel = {
                event(ScheduleAppointmentEvents.OnConfirmationDialogCancel)
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.confirmacionDeCita),
            style = MaterialTheme.typography.titleLarge,
            color = AlwaysBlack
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AlwaysWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                DetailRow(
                    label = stringResource(id = R.string.sucursal_label),
                    value = uiState.branchName.orEmpty(),
                    iconImage = R.drawable.place_24p_negro
                )
                DetailRow(
                    label = stringResource(id = R.string.especialista_label),
                    value = uiState.staffName.orEmpty(),
                    iconImage = R.drawable.face_unlock_24px
                )
                DetailRow(
                    label = stringResource(id = R.string.servicio_label),
                    value = uiState.serviceName,
                    iconImage = R.drawable.stars_24px
                )
                DetailRow(
                    label = stringResource(id = R.string.fecha_label),
                    value = uiState.date,
                    iconImage = R.drawable.insert_invitation_24px
                )
                DetailRow(
                    label = stringResource(id = R.string.hora_label),
                    value = uiState.time,
                    iconImage = R.drawable.watch_later_24px,
                    showDivider = false
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = AlwaysBlack.copy(alpha = 0.1f)
                )

                PromoCodeSection(
                    promoCode = uiState.promoCode,
                    isApplied = uiState.isPromoApplied,
                    error = uiState.promoError,
                    isLoading = uiState.validatingPromo,
                    onCodeChange = { event(ScheduleAppointmentEvents.OnPromoCodeChanged(it)) },
                    onApply = { event(ScheduleAppointmentEvents.OnApplyPromoCode) }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = AlwaysBlack.copy(alpha = 0.1f)
                )

                val originalPrice = uiState.servicePrice.toDoubleOrNull() ?: 0.0
                PriceSummary(
                    originalPrice = originalPrice,
                    appliedPromo = uiState.appliedPromo
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        ConfirmButton(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            event(ScheduleAppointmentEvents.OnSaveAppointment)
        }
    }
}

@Composable
private fun PromoCodeSection(
    promoCode: String,
    isApplied: Boolean,
    error: String?,
    isLoading: Boolean,
    onCodeChange: (String) -> Unit,
    onApply: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = stringResource(R.string.promotional_code),
            style = MaterialTheme.typography.labelMedium,
            color = AlwaysBlack.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = promoCode,
                onValueChange = onCodeChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_your_code),
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                enabled = !isApplied && !isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = AlwaysBlack.copy(alpha = 0.2f)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onApply,
                enabled = !isApplied && promoCode.isNotBlank() && !isLoading,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(52.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = AlwaysWhite,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(if (isApplied) stringResource(R.string.applied) else stringResource(R.string.apply))
                }
            }
        }
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
        if (isApplied) {
            Text(
                text = stringResource(R.string.coupon_applied_successfully),
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
private fun PriceSummary(
    originalPrice: Double,
    appliedPromo: PromotionCode?
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        if (appliedPromo != null) {
            val discount = originalPrice * appliedPromo.discountPercentage / 100.0
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Subtotal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlwaysBlack.copy(alpha = 0.6f)
                )
                Text(
                    originalPrice.toCurrencyString(Currency.USD),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlwaysBlack
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        R.string.discount_percentage,
                        appliedPromo.discountPercentage
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4CAF50)
                )
                Text(
                    "-${discount.toCurrencyString(Currency.USD)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4CAF50)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.Total),
                style = MaterialTheme.typography.titleMedium,
                color = AlwaysBlack
            )
            val finalPrice = if (appliedPromo != null) {
                originalPrice - (originalPrice * appliedPromo.discountPercentage / 100.0)
            } else {
                originalPrice
            }
            Text(
                text = finalPrice.toCurrencyString(Currency.USD),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xff051721)
            )
        }
    }
}

@Composable
private fun ConfirmButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff051721),
            contentColor = AlwaysWhite
        ),
        onClick = { onClick.invoke() }
    ) {
        Text(
            text = stringResource(id = R.string.confirma_cita),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    sizeIcon: Dp = 22.dp,
    @DrawableRes iconImage: Int,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconImage),
                contentDescription = label,
                colorFilter = ColorFilter.tint(Color(0xff051721)),
                modifier = Modifier.size(sizeIcon)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = AlwaysBlack.copy(alpha = 0.6f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = AlwaysBlack
                )
            }
        }
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 54.dp, end = 16.dp),
                color = AlwaysBlack.copy(alpha = 0.08f)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScheduleConfirmationScreenContentPreview() {
    ScheduleConfirmationScreenContent(
        uiState = ScheduleAppointmentState(
            servicePrice = "150",
            date = "12/09/2024",
            time = "12:30 am",
            serviceName = "Haircut",
            branchName = "Zacatecas",
            staffName = "Orlando"
        ),
        event = {}
    )
}
