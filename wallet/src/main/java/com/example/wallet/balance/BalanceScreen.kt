package com.example.wallet.balance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.BaseScreenState
import com.example.core.ui.base.getContentOrNull
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.components.skeletons.BranchesScreenSkeletons
import com.example.core.ui.theme.Background
import com.example.core.ui.theme.CardSurface
import com.example.core.ui.theme.DashedLine
import com.example.core.ui.theme.StatusBarColor
import com.example.core.ui.theme.TextMuted
import com.example.core.ui.theme.TextPrimary
import com.example.core.ui.theme.TextSecondary
import com.example.domain.wallet.Currency
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun WalletScreen(
    navController: NavController,
    balanceViewModel: BalanceViewmodel = hiltViewModel()
) {
    val viewState by balanceViewModel.state.collectAsStateWithLifecycle()
    when (viewState) {
        BaseScreenState.OnLoading -> {
            BranchesScreenSkeletons()//Todo add skeletons screen for balanceScreen
        }

        is BaseScreenState.OnContent -> {

            viewState.getContentOrNull()?.let { state ->
                BaseComposeScreen(
                    toolbarConfiguration = ToolbarConfiguration(title = "Balance"),
                    navController = navController
                ) {
                    WalletScreenContent(state = state)
                }
            }

        }


        is BaseScreenState.OnError -> {
            BaseErrorScreen()
        }
    }
}

@Composable
private fun WalletScreenContent(
    state: BalanceUiState,
    onTopUpClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(20.dp)
    ) {
        WalletHeader(userName = state.userName)

        Spacer(modifier = Modifier.height(20.dp))

        WalletBalanceCard(state = state)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onTopUpClick,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StatusBarColor)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Top up", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            OutlinedButton(
                onClick = onHistoryClick,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = TextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "History",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }
        }
    }


}

@Composable
private fun WalletHeader(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good evening",
                fontSize = 13.sp,
                color = TextSecondary
            )
            Text(
                text = userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(StatusBarColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initialsFrom(userName),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = StatusBarColor
            )
        }
    }
}

@Composable
private fun WalletBalanceCard(state: BalanceUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardSurface)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "WALLET BALANCE",
            fontSize = 12.sp,
            letterSpacing = 0.06.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = formatBalance(state.balance),
                fontSize = 34.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Text(
                text = ".00",
                fontSize = 16.sp,
                color = TextMuted,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }

        Text(
            text = state.currency.name,//
            fontSize = 12.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(18.dp))

        DashedDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = TextMuted
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Member since ${formatCreatedAt(state.createdAtMillis)}",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
            Text(
                text = "ID •••${state.userId.takeLast(5)}",
                fontSize = 12.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun DashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            brush = SolidColor(DashedLine),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
        )
    }
}

private fun initialsFrom(name: String): String =
    name.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

private fun formatBalance(balance: Long): String =
    NumberFormat.getNumberInstance(Locale.US).format(balance).let { "$$it" }

private fun formatCreatedAt(millis: Long): String =
    SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(Date(millis))

@Preview(showBackground = true)
@Composable
private fun WalletScreenPreview() {
    MaterialTheme {
        WalletScreenContent(
            state = BalanceUiState(
                userName = "Android Developer",
                balance = 5457,
                currency = Currency.USD,
                createdAtMillis = 1787166685299L,
                userId = "9oMgcrsUZZZvWwAIk4go1ONOha52"
            )
        )
    }
}