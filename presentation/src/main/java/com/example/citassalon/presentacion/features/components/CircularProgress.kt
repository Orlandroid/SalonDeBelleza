package com.example.citassalon.presentacion.features.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .width(64.dp)
            .height(64.dp),
        color = Color(0xFF3700B3),
        strokeWidth = 5.dp
    )
}