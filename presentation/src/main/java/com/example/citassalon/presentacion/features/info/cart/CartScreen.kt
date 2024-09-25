package com.example.citassalon.presentacion.features.info.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration

@Composable
fun CartScreen(navController: NavController) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.staff))
    ) {
        CartScreenContent()
    }

}

@Composable
fun CartScreenContent(modifier: Modifier = Modifier) {


}


@Composable
@Preview(showBackground = true)
fun CartScreenContentPreview() {
    CartScreenContent()

}


