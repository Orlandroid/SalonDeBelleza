package com.example.citassalon.presentacion.features.info.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration

@Composable
fun ServicesScreen(navController: NavController) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = "Servicios")
    ) {
        ServicesScreenContent()
    }

}

@Composable
fun ServicesScreenContent(modifier: Modifier = Modifier) {


}


@Composable
@Preview(showBackground = true)
fun ServicesScreenContentPreview() {
    ServicesScreenContent()

}


