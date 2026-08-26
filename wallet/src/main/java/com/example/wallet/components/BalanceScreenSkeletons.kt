package com.example.wallet.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.base.MediumSpacer
import com.example.core.ui.base.Orientation
import com.example.core.ui.components.shimmerBrush
import com.example.core.ui.theme.Background
import com.example.core.ui.theme.CardSurface
import com.example.core.ui.theme.DashedLine
import com.example.core.util.toCurrencyString
import com.example.domain.wallet.Currency
import com.example.wallet.R
import com.example.wallet.balance.BalanceUiState


@Composable
fun BalanceScreenSkeletons(
) {
    WalletScreenContent(BalanceUiState())
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
        WalletHeader()

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
                    .background(shimmerBrush())
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Transparent
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.top_up),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Transparent
                )
            }

            OutlinedButton(
                onClick = onHistoryClick,
                modifier = Modifier
                    .background(shimmerBrush())
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Transparent
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "History",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Transparent
                )
            }
        }
    }


}

@Composable
private fun WalletHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier.background(shimmerBrush()),
                text = stringResource(R.string.good_evening),
                fontSize = 13.sp,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier.background(shimmerBrush()),
                text = "Android Developer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Transparent
            )
        }

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(shimmerBrush()),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Transparent
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
            modifier = Modifier.background(shimmerBrush()),
            text = stringResource(R.string.wallet_balance),
            fontSize = 12.sp,
            letterSpacing = 0.06.sp,
            color = Color.Transparent
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                modifier = Modifier.background(shimmerBrush()),
                text = state.balance.toCurrencyString(Currency.USD),
                fontSize = 34.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Transparent
            )
            Text(
                text = ".00",
                fontSize = 16.sp,
                color = Color.Transparent,
                modifier = Modifier
                    .padding(bottom = 3.dp)
                    .background(shimmerBrush())
            )
        }

        Text(
            modifier = Modifier.background(shimmerBrush()),
            text = state.currency.name,
            fontSize = 12.sp,
            color = Color.Transparent
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
                    modifier = Modifier
                        .size(14.dp)
                        .background(shimmerBrush()),
                    tint = Color.Transparent
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    text = "Member since $",
                    fontSize = 12.sp,
                    color = Color.Transparent
                )
            }
            Text(
                modifier = Modifier.background(shimmerBrush()),
                text = "ID •••${state.userId.takeLast(5)}",
                fontSize = 12.sp,
                color = Color.Transparent
            )
        }
    }
}

@Composable
private fun DashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .background(shimmerBrush())
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


@Composable
private fun Branches() {
    LazyColumn {
        for (x in 0..5) {
            item {
                MediumSpacer(orientation = Orientation.VERTICAL)
                Spacer(
                    Modifier
                        .height(56.dp)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(shimmerBrush())
                )
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
private fun BranchesScreenSkeletonsPreview() {
    BalanceScreenSkeletons()
}