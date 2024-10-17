package com.example.citassalon.presentacion.features.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BaseScreenState
import com.example.citassalon.presentacion.features.theme.Background
import kotlinx.coroutines.flow.StateFlow


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
    alertDialogMessagesConfig: AlertDialogMessagesConfig = AlertDialogMessagesConfig(),
    background: Color = Background,
    state: BaseScreenState<T>,
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
    state: BaseScreenState<T>,
    alertDialogMessagesConfig: AlertDialogMessagesConfig,
    content: @Composable () -> Unit,
) {
    when (state) {
        is BaseScreenState.Error -> {
            ErrorState(alertDialogMessagesConfig)
        }

        is BaseScreenState.Loading -> {
            ProgressDialog()
        }

        is BaseScreenState.Success -> {
            content()
        }

        is BaseScreenState.ErrorNetwork -> {
            ErrorNetworkState(alertDialogMessagesConfig)
        }
    }
}

@Composable
fun ErrorState(alertDialogMessagesConfig: AlertDialogMessagesConfig) {
    val shouldShowDialogError = remember { mutableStateOf(true) }
    if (shouldShowDialogError.value) {
        BaseAlertDialogMessages(
            alertDialogMessagesConfig = alertDialogMessagesConfig.copy(kindOfMessage = KindOfMessage.ERROR,
                title = R.string.error,
                bodyMessage = R.string.error_al_obtener_datos,
                onConfirmation = {
                    alertDialogMessagesConfig.onConfirmation.invoke()
                    shouldShowDialogError.value = false
                }
            ),
            onDismissRequest = {},
        )
    }
}

@Composable
fun ErrorNetworkState(alertDialogMessagesConfig: AlertDialogMessagesConfig) {
    val shouldShowDialogNetworkError = remember { mutableStateOf(true) }
    if (shouldShowDialogNetworkError.value) {
        BaseAlertDialogMessages(
            onDismissRequest = {

            },
            alertDialogMessagesConfig = alertDialogMessagesConfig.copy(kindOfMessage = KindOfMessage.ERROR,
                title = R.string.network_error,
                bodyMessage = R.string.network_error_message,
                onConfirmation = {
                    alertDialogMessagesConfig.onConfirmation.invoke()
                    shouldShowDialogNetworkError.value = false
                }
            )
        )
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