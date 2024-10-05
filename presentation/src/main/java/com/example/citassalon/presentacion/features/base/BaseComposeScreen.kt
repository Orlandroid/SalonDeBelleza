package com.example.citassalon.presentacion.features.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.components.Toolbar
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BaseScreenState
import com.example.citassalon.presentacion.features.theme.Background


@Composable
fun BaseComposeScreen(
    navController: NavController,
    toolbarConfiguration: ToolbarConfiguration = ToolbarConfiguration(),
    background: Color = Background,
    isLoading: Boolean = false,
    content: @Composable () -> Unit,
) {
    Scaffold(topBar = {
        if (toolbarConfiguration.showToolbar) {
            Toolbar(
                navController = navController, toolbarConfiguration = toolbarConfiguration
            )
        }
    }) { paddingValues ->
        ContentScreen(paddingValues = paddingValues, background = background) {
            if (isLoading) {
                ProgressDialog()
            }
            content()
        }
    }
}

@Composable
fun <T> BaseComposeScreenState(
    navController: NavController,
    toolbarConfiguration: ToolbarConfiguration = ToolbarConfiguration(),
    alertDialogMessagesConfig: AlertDialogMessagesConfig = AlertDialogMessagesConfig(
        title = R.string.test_text,
        bodyMessage = R.string.message_body,
        buttonText = R.string.aceptar
    ),
    background: Color = Background,
    state: State<BaseScreenState<T>>,
    content: @Composable () -> Unit,
) {
    Scaffold(topBar = {
        if (toolbarConfiguration.showToolbar) {
            Toolbar(
                navController = navController, toolbarConfiguration = toolbarConfiguration
            )
        }
    }) { paddingValues ->
        ContentScreen(
            paddingValues = paddingValues, background = background
        ) {
            BaseStateScreen(
                state = state, alertDialogMessagesConfig = alertDialogMessagesConfig
            ) {
                content()
            }
        }
    }
}

@Composable
fun <T> BaseStateScreen(
    state: State<BaseScreenState<T>>,
    alertDialogMessagesConfig: AlertDialogMessagesConfig,
    content: @Composable () -> Unit,
) {
    when (state.value) {
        is BaseScreenState.Error -> {
            BaseAlertDialogMessages(
                alertDialogMessagesConfig = alertDialogMessagesConfig,
                onConfirmation = {},
                onDismissRequest = {},
            )
        }

        is BaseScreenState.Loading -> {
            ProgressDialog()
        }

        is BaseScreenState.Success -> {
            content()
        }

        is BaseScreenState.ErrorNetwork -> {
            BaseAlertDialogMessages(
                onConfirmation = {},
                onDismissRequest = {},
                alertDialogMessagesConfig = alertDialogMessagesConfig
            )
        }
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