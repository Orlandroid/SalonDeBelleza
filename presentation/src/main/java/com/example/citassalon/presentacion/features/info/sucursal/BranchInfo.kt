package com.example.citassalon.presentacion.features.info.sucursal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun BranchInfoScreen(navController: NavController) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = "Branch Name")
    ) {
        BranchInfoScreenContent(
            goTOServicesScreen = { navController.navigate(InfoNavigationScreens.ServicesRoute) },
            goToOurStaffScreen = { navController.navigate(InfoNavigationScreens.OurStaffRoute) },
            goToLocationScreen = { navController.navigate(InfoNavigationScreens.LocationRoute) }
        )
    }
}

@Composable
private fun BranchInfoScreenContent(
    modifier: Modifier = Modifier,
    goToOurStaffScreen: () -> Unit,
    goTOServicesScreen: () -> Unit,
    goToLocationScreen: () -> Unit
) {
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val myGuideline = createGuidelineFromTop(0.40f)
        val (content) = createRefs()
        Card(
            Modifier.constrainAs(content) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                top.linkTo(myGuideline)
                linkTo(parent.start, parent.end)
                bottom.linkTo(parent.bottom)
            }, colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            BranchInfoMenu(
                goToOurStaffScreen = goToOurStaffScreen,
                goTOServicesScreen = goTOServicesScreen,
                goToLocationScreen = goToLocationScreen
            )
        }
    }
}

@Composable
private fun BranchInfoMenu(
    modifier: Modifier = Modifier,
    goToOurStaffScreen: () -> Unit,
    goTOServicesScreen: () -> Unit,
    goToLocationScreen: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(R.string.staff)
                ) {
                    goToOurStaffScreen.invoke()
                }
            )
        }
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(R.string.servicios)
                ) {
                    goTOServicesScreen.invoke()
                }
            )
        }
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(R.string.ubicacion)
                ) {
                    goToLocationScreen.invoke()
                }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun BranchInfoScreenContentPreview() {
    BranchInfoScreenContent(
        goTOServicesScreen = {},
        goToLocationScreen = {},
        goToOurStaffScreen = {}
    )
}


