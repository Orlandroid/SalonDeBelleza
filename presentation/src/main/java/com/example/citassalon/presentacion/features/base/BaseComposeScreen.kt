package com.example.citassalon.presentacion.features.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.components.AppProgress
import com.example.citassalon.presentacion.features.components.Toolbar
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background


@Composable
fun BaseComposeScreen(
    navController: NavController,
    toolbarConfiguration: ToolbarConfiguration = ToolbarConfiguration(),
    background: Color = Background,
    isLoading: Boolean = false,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            if (toolbarConfiguration.showToolbar) {
                Toolbar(
                    navController = navController,
                    toolbarConfiguration = toolbarConfiguration
                )
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            LoadingScreen()
        } else {
            ContentScreen(paddingValues = paddingValues, background = background) {
                content()
            }
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        AppProgress(modifier.align(Alignment.Center))
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    background: Color,
    content: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(background)
    ) {
        content.invoke()
    }
}


@Composable
@Preview(showBackground = true)
fun BaseViewPreview() {
    BaseComposeScreen(navController = rememberNavController()) {
        Text(text = "I am just trying to be my best software engineer version")
    }
}