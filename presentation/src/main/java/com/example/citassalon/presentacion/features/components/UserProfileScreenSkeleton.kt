package com.example.citassalon.presentacion.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.theme.Background


@Composable
fun UserProfileScreenSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            Modifier
                .size(144.dp)
                .clip(CircleShape)
                .background(shimmerBrush())
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.background(shimmerBrush()),
                color = Color.Transparent,
                text = stringResource(R.string.name_user),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(24.dp))
            Spacer(
                Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(shimmerBrush())
            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            BaseLabel(key = stringResource(R.string.name_user))
            HorizontalDivider()
            BaseLabel(key = stringResource(R.string.phone))
            HorizontalDivider()
            BaseLabel(key = stringResource(R.string.label_email))
            HorizontalDivider()
            BaseLabel(key = stringResource(R.string.money))
            HorizontalDivider()
        }
    }
}

@Composable
private fun BaseLabel(
    key: String
) {
    Text(
        modifier = Modifier
            .padding(4.dp)
            .background(shimmerBrush()),
        color = Color.Transparent,
        text = key,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .padding(4.dp)
            .background(shimmerBrush()),
        color = Color.Transparent,
        text = key
    )
}

@Composable
@Preview(showBackground = true)
private fun CharacterDetailSkeletonPreview() {
    UserProfileScreenSkeleton()
}