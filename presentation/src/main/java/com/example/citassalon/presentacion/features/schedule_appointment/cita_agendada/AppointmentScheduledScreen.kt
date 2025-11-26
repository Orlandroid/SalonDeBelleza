package com.example.citassalon.presentacion.features.schedule_appointment.cita_agendada

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.LongSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite

@Composable
fun AppointmentScheduledScreen(
    navController: NavController,
    closeFlow: () -> Unit
) {
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false),
        navController = navController
    ) {
        AppointmentScheduledContent(closeFlow = closeFlow)
    }

}

@Composable
private fun AppointmentScheduledContent(
    modifier: Modifier = Modifier,
    closeFlow: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login))
        LottieAnimation(
            iterations = 1,
            composition = composition,
            modifier = modifier
                .fillMaxWidth()
                .height(450.dp)
        )
        val progress by animateLottieCompositionAsState(composition)
        val animationComplete = 1.0f
        if (progress == animationComplete) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                color = AlwaysBlack,
                text = stringResource(R.string.cita_agendada),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            LongSpacer(orientation = Orientation.VERTICAL)
            AcceptButton(closeFlow = closeFlow)
        }
    }
}

@Composable
private fun AcceptButton(
    closeFlow: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF051721)),
        onClick = closeFlow
    ) {
        Text(
            color = AlwaysWhite,
            text = stringResource(R.string.aceptar),
            modifier = Modifier
                .padding(horizontal = 32.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun AppointmentScheduledContentPreview() {
    AppointmentScheduledContent(
        modifier = Modifier,
        closeFlow = {}
    )

}


