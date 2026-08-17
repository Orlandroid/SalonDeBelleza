package com.example.info.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration

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



