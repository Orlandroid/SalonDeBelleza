package com.example.citassalon.presentacion.features.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppProgress(modifier: Modifier = Modifier, strokeWidth: Dp = 5.dp) {
    CircularProgressIndicator(
        modifier = modifier
            .width(64.dp),
        color = Color(0xFF3700B3),
        strokeWidth = strokeWidth
    )
}