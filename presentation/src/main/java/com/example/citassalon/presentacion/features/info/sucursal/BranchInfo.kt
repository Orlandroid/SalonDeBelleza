package com.example.citassalon.presentacion.features.info.sucursal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    Column(
        modifier = modifier
            .background(Background)
            .fillMaxSize()
    ) {
        Spacer(Modifier.weight(0.4f))
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
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
                    text = stringResource(R.string.staff),
                    clickOnItem = goToOurStaffScreen
                )
            )
        }
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(R.string.servicios),
                    clickOnItem = goTOServicesScreen
                )
            )
        }
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(R.string.ubicacion),
                    clickOnItem = goToLocationScreen
                )
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


