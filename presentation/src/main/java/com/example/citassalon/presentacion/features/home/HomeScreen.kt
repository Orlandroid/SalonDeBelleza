package com.example.citassalon.presentacion.features.home

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
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.navigation.Screens
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.features.theme.StatusBarColor

@Composable
fun HomeScreen(navController: NavHostController) {
    ConstraintLayout(
        Modifier
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
            })
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
            })
        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = AlwaysWhite
            ),
            onClick = {
//                    navigate(HomeFragmentDirections.actionHome3ToAgendarSucursal())
            },
            modifier = Modifier
                .constrainAs(btnSchedule) {
                    top.linkTo(myGuideline)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
        ) {
            Text(
                color = AlwaysBlack,
                text = stringResource(id = R.string.agendar_button),
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
        }
        FloatingActionButton(
            modifier = Modifier.constrainAs(floatingLeft) {
                start.linkTo(parent.start, 48.dp)
                bottom.linkTo(parent.bottom, 60.dp)
            },
            onClick = {
//                    navigate(R.id.nav_info)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_list_24),
                contentDescription = null
            )

        }
        FloatingActionButton(
            modifier = Modifier.constrainAs(floatingProfile) {
                end.linkTo(parent.end, 48.dp)
                bottom.linkTo(parent.bottom, 60.dp)
            },
            onClick = {
                navController.navigate(Screens.ProfileScreen.route)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    HomeScreen(rememberNavController())
}