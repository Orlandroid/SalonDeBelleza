package com.example.citassalon.presentacion.features.info.ubicacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration

@Composable
fun LocationScreen(navController: NavController) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.ubicacion))
    ) {
        LocationScreenContent()
    }

}

@Composable
fun LocationScreenContent(modifier: Modifier = Modifier) {


}


@Composable
@Preview(showBackground = true)
fun LocationScreenContentPreview() {
    LocationScreenContent()

}


