package com.example.citassalon.presentacion.features.base

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.components.Toolbar
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.theme.Background


@Composable
fun <T> BaseComposeScreenState(
    navController: NavController,
    toolbarConfiguration: ToolbarConfiguration = ToolbarConfiguration(),
    alertDialogMessagesConfig: AlertDialogMessagesConfig = AlertDialogMessagesConfig(
        bodyMessage = stringResource(
            R.string.error
        )
    ),
    background: Color = Background,
    state: BaseScreenState<T>,
    onSuccess: @Composable (result: T) -> Unit,
) {
    Scaffold(topBar = {
        if (toolbarConfiguration.showToolbar) {
            Toolbar(
                navController = navController, toolbarConfiguration = toolbarConfiguration
            )
        }
    }
    ) { paddingValues ->
        ContentScreen(
            paddingValues = paddingValues, background = background
        ) {
            ObserveBaseState(
                state = state, alertDialogMessagesConfig = alertDialogMessagesConfig
            ) { mResult ->
                onSuccess(mResult)
            }
        }
    }
}

@Composable
fun <T> ObserveBaseState(
    state: BaseScreenState<T>,
    alertDialogMessagesConfig: AlertDialogMessagesConfig,
    onSuccess: @Composable (T) -> Unit,
) {
    when (state) {
        is BaseScreenState.Error -> {
            ErrorState(
                alertDialogMessagesConfig.copy(
                    bodyMessage = state.exception.message ?: stringResource(R.string.error)
                )
            )
        }

        is BaseScreenState.Loading -> {
            ProgressDialog()
        }

        is BaseScreenState.Success -> {
            onSuccess(state.data)
        }

        is BaseScreenState.ErrorNetwork -> {
            ErrorNetworkState(alertDialogMessagesConfig)
        }

        is BaseScreenState.Idle -> {}
    }
}

@Composable
fun ErrorState(alertDialogMessagesConfig: AlertDialogMessagesConfig) {
    val shouldShowDialogError = remember { mutableStateOf(true) }
    if (shouldShowDialogError.value) {
        BaseAlertDialogMessages(
            alertDialogMessagesConfig = alertDialogMessagesConfig.copy(kindOfMessage = KindOfMessage.ERROR,
                title = R.string.error,
                bodyMessage = alertDialogMessagesConfig.bodyMessage,
                onConfirmation = {
                    alertDialogMessagesConfig.onConfirmation.invoke()
                    shouldShowDialogError.value = false
                }),
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
                bodyMessage = stringResource(R.string.network_error_message),
                onConfirmation = {
                    alertDialogMessagesConfig.onConfirmation.invoke()
                    shouldShowDialogNetworkError.value = false
                }
            )
        )
    }
}