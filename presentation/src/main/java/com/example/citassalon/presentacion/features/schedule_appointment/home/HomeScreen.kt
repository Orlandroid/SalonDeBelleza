package com.example.citassalon.presentacion.features.schedule_appointment.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.app_navigation.MainActivityCompose
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleAppointmentScreens
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.features.theme.StatusBarColor

@Composable
fun HomeScreen(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit
) {
    val activity = LocalContext.current as Activity
    BackHandler {
        (activity as MainActivityCompose).finish()
    }
    HomeScreenContent(
        modifier = Modifier,
        goToInfoNavigation = goToInfoNavigation,
        goToProfileNavigation = goToProfileNavigation,
        goToBranchesScreen = {
            navController.navigate(ScheduleAppointmentScreens.ChoseBranchRoute)
        }
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit,
    goToBranchesScreen: () -> Unit
) {
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val myGuideline = createGuidelineFromTop(0.40f)
        val (imageLogo, appName, btnSchedule, containerImage, floatingLeft, floatingProfile) = createRefs()
        val logoImage = painterResource(id = R.drawable.logo)
        ConstraintLayout(
            Modifier
                .background(StatusBarColor)
                .constrainAs(containerImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(myGuideline)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        ) {
        }
        Image(painter = logoImage,
            contentDescription = null,
            modifier = Modifier.constrainAs(imageLogo) {
                top.linkTo(parent.top, 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                bottom.linkTo(myGuideline, 24.dp)
            }
        )
        Text(
            fontSize = 30.sp,
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.constrainAs(appName) {
                top.linkTo(containerImage.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(btnSchedule.top)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
        )
        ButtonSchedule(
            modifier = Modifier
                .constrainAs(btnSchedule) {
                    top.linkTo(myGuideline)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
            goToBranchesScreen = goToBranchesScreen
        )
        FloatingButtonInfo(
            modifier = Modifier.constrainAs(floatingLeft) {
                start.linkTo(parent.start, 48.dp)
                bottom.linkTo(parent.bottom, 60.dp)
            },
            goToInfoNavigation = goToInfoNavigation
        )
        FloatingButtonProfile(
            modifier = Modifier.constrainAs(floatingProfile) {
                end.linkTo(parent.end, 48.dp)
                bottom.linkTo(parent.bottom, 60.dp)
            },
            goToProfileNavigation = goToProfileNavigation
        )
    }
}

@Composable
private fun ButtonSchedule(
    modifier: Modifier = Modifier,
    goToBranchesScreen: () -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = AlwaysWhite
        ),
        onClick = {
            goToBranchesScreen()
        },
        modifier = modifier
    ) {
        Text(
            color = AlwaysBlack,
            text = stringResource(id = R.string.agendar_button),
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        )
    }
}

@Composable
private fun FloatingButtonInfo(
    modifier: Modifier = Modifier,
    goToInfoNavigation: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            goToInfoNavigation()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_list_24),
            contentDescription = null
        )
    }
}

@Composable
private fun FloatingButtonProfile(
    modifier: Modifier = Modifier,
    goToProfileNavigation: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            goToProfileNavigation()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_person_24),
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    HomeScreenContent(
        goToBranchesScreen = {},
        goToProfileNavigation = {},
        goToInfoNavigation = {}
    )
}