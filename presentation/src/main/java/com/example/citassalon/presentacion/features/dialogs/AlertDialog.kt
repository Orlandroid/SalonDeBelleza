package com.example.citassalon.presentacion.features.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.theme.Danger
import com.example.citassalon.presentacion.features.theme.Info
import com.example.citassalon.presentacion.features.theme.Success
import com.example.citassalon.presentacion.features.theme.Waring

@Composable
fun BaseAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector = Icons.Default.Info,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(icon, contentDescription = "IconInfo")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.aceptar))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.cancelar_dialog))
            }
        }
    )
}


@Composable
@Preview(showBackground = true)
fun BaseAlertDialogPreview(modifier: Modifier = Modifier) {
    BaseAlertDialog(
        dialogTitle = "Alert dialog example",
        dialogText = "This is an example of an alert dialog with buttons",
        icon = Icons.Default.Info,
        onDismissRequest = {},
        onConfirmation = {}
    )
}

enum class StatesHeaderDialog(color: Color) {
    SUCCESS(Success),
    WARING(Waring),
    ERROR(Danger),
    INFO(Info)
}