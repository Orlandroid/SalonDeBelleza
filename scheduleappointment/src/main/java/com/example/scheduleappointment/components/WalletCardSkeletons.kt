package com.example.scheduleappointment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.components.shimmerBrush
import com.example.core.ui.theme.BackgroundListsMainFlow
import com.example.scheduleappointment.R


@Composable
fun WalletCardSkeletons(
) {
    Card(
        modifier = Modifier.padding(horizontal = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundListsMainFlow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                modifier = Modifier.background(shimmerBrush()),
                text = stringResource(R.string.my_wallet),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Transparent
            )
            Spacer(Modifier.padding(top = 12.dp))
            Text(
                modifier = Modifier.background(shimmerBrush()),
                text = "$3,996.00 USD",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Transparent
            )
            Spacer(Modifier.padding(top = 12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    text = stringResource(R.string.show_wallet),
                    fontSize = 14.sp,
                    color = Color.Transparent
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .background(shimmerBrush()),
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.Transparent
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun WalletCardSkeletonsPreview() {
    WalletCardSkeletons()
}