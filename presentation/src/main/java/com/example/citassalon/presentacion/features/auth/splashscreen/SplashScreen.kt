package com.example.citassalon.presentacion.features.auth.splashscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.auth.AuthScreens
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.StatusBarColor


@Composable
fun SplashScreen(
    navController: NavController,
    isActiveSession: Boolean = false,
    goToScheduleNav: () -> Unit
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false)
    ) {
        SplashScreenContent(
            isActiveSession = isActiveSession,
            goToLogin = { navController.navigate(AuthScreens.LoginRoute) },
            goToScheduleNav = goToScheduleNav
        )
    }
}


@Composable
private fun SplashScreenContent(
    isActiveSession: Boolean = false,
    goToScheduleNav: () -> Unit,
    goToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StatusBarColor),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            text = stringResource(id = R.string.skeduly),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
        Image(
            alignment = Alignment.Center,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "ImageSplashScreen"
        )
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.weight(1f))
        AnimatedVisibility(
            visible = true,
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            if (isActiveSession) {
                goToScheduleNav.invoke()
            } else {
                goToLogin.invoke()
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SplashScreenContentPreview() {
    SplashScreenContent(
        isActiveSession = true,
        goToLogin = {},
        goToScheduleNav = {}
    )
}



    
