package com.example.citassalon.presentacion.features.components.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citassalon.presentacion.features.base.LongSpacer
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.shimmerBrush
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.features.theme.BackgroundListsMainFlow


@Composable
fun BranchesScreenSkeletons(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BackgroundListsMainFlow)
        ) {
            Branches()
            LongSpacer(orientation = Orientation.VERTICAL)
        }
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
    BranchesScreenSkeletons()
}