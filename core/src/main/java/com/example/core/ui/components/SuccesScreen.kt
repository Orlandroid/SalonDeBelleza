package com.example.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.R
import com.example.core.ui.theme.AlwaysBlack

@Composable
fun SuccessScreen(
    title: String,
    description: String? = null,
    closeFlow: () -> Unit
) {
    SuccessContent(
        title = title,
        description = description,
        closeFlow = closeFlow
    )
}

@Composable
private fun SuccessContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String?,
    closeFlow: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.login)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    LaunchedEffect(progress) {
        if (progress >= 1f) {
            closeFlow()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            color = AlwaysBlack
        )

        if (description != null) {
            Text(
                text = description,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SuccesScreenPreview() {
    SuccessScreen(
        title = "Removed",
        description = "Operacion realizada con exito.",
        closeFlow = { }
    )
}