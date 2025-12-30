package com.example.citassalon.presentacion.features.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage


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