package com.example.citassalon.presentacion.features.splashscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.navigation.Screens
import com.example.citassalon.presentacion.features.theme.StatusBarColor


@Composable
fun SplashScreenV1(
    navController: NavController,
    isActiveSession: Boolean = false
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false)
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(StatusBarColor)
        ) {
            val (image, text) = createRefs()
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                text = stringResource(id = R.string.skeduly),
                modifier = Modifier.constrainAs(text) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, image.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "ImageSplashScreen",
                modifier = Modifier.constrainAs(image) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(300.dp)
                }
            )
            //Todo add efect of fadeout to the all screen
            AnimatedVisibility(
                visible = true,
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                if (isActiveSession) {
                    navController.navigate(Screens.HomeScreen.route)
                } else {
                    navController.navigate(Screens.LoginScreen.route)
                }

            }
        }
    }
}
    
