package com.example.citassalon.presentacion.features.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.base.SmallSpacer
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Azul
import com.example.citassalon.presentacion.features.theme.Danger
import com.example.citassalon.presentacion.features.theme.Info
import com.example.citassalon.presentacion.features.theme.Success
import com.example.citassalon.presentacion.features.theme.Waring

@Composable
fun BaseAlertDialogMessages(
    modifier: Modifier = Modifier,
    alertDialogMessagesConfig: AlertDialogMessagesConfig = AlertDialogMessagesConfig(
        title = R.string.title,
        bodyMessage = stringResource(R.string.message_body),
        buttonText = R.string.aceptar,
    ),
    onDismissRequest: () -> Unit = {}
) {
    BaseCustomDialog(
        modifier = modifier, onDismissRequest = onDismissRequest
    ) {
        BaseAlertDialogMessagesContent(
            onConfirmation = alertDialogMessagesConfig.onConfirmation,
            alertDialogMessagesConfig = alertDialogMessagesConfig,
        )
    }
}

@Composable
private fun BaseAlertDialogMessagesContent(
    modifier: Modifier = Modifier,
    alertDialogMessagesConfig: AlertDialogMessagesConfig,
    onConfirmation: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AlertTitle(
            kindOfMessage = alertDialogMessagesConfig.kindOfMessage,
            modifier = Modifier,
            title = alertDialogMessagesConfig.title,
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .wrapContentHeight(),
            text = alertDialogMessagesConfig.bodyMessage,
            fontSize = 18.sp,
            color = AlwaysBlack,
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        AlertButtonOrButtons(
            modifier = Modifier,
            acceptText = alertDialogMessagesConfig.buttonText,
            isTwoButtonsAlert = alertDialogMessagesConfig.isTwoButtonsAlert,
            onAccept = onConfirmation
        )
    }
}

@Composable
private fun AlertButtonOrButtons(
    modifier: Modifier = Modifier,
    @StringRes acceptText: Int,
    onAccept: () -> Unit,
    isTwoButtonsAlert: IsTwoButtonsAlert?
) {
    if (isTwoButtonsAlert == null) {
        AlertButton(
            modifier = modifier,
            buttonMessage = acceptText, onClick = {
                onAccept.invoke()
            }
        )
    } else {
        Row(
            modifier = Modifier
        ) {
            AlertButton(
                isRounded = true,
                modifier = modifier
                    .weight(1f)
                    .padding(start = 4.dp, bottom = 4.dp),
                buttonMessage = acceptText,
                onClick = {
                    onAccept.invoke()
                }
            )
            SmallSpacer(
                orientation = Orientation.HORIZONTAL
            )
            AlertButton(
                isRounded = true,
                modifier = modifier
                    .weight(1f)
                    .padding(end = 4.dp, bottom = 4.dp),
                buttonMessage = isTwoButtonsAlert.cancelText,
                onClick = {
                    isTwoButtonsAlert.clickOnCancel.invoke()
                }
            )
            SmallSpacer(orientation = Orientation.VERTICAL)
        }
    }
}

@Composable
private fun AlertTitle(
    kindOfMessage: KindOfMessage,
    modifier: Modifier = Modifier, @StringRes title: Int
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = kindOfMessage.color),
        shape = RectangleShape,
    ) {
        SmallSpacer(orientation = Orientation.VERTICAL)
        Text(
            textAlign = TextAlign.Center,
            color = AlwaysWhite,
            fontSize = 24.sp,
            text = stringResource(title),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AlertButton(
    modifier: Modifier = Modifier,
    isRounded: Boolean = false,
    @StringRes buttonMessage: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        colors = CardDefaults.cardColors(containerColor = Azul),
        shape = if (isRounded) {
            RoundedCornerShape(8.dp)
        } else {
            RectangleShape
        }
    ) {
        SmallSpacer(orientation = Orientation.VERTICAL)
        Text(
            textAlign = TextAlign.Center,
            color = AlwaysWhite,
            fontSize = 24.sp,
            text = stringResource(buttonMessage),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
@Preview(showBackground = true)
fun BaseAlertDialogPreview() {
    BaseAlertDialogMessages(modifier = Modifier, onDismissRequest = {})
}

@Composable
@Preview(showBackground = true)
fun BaseAlertDialogPreviewTwoButton() {
    BaseAlertDialogMessages(
        modifier = Modifier,
        onDismissRequest = {},
        alertDialogMessagesConfig = AlertDialogMessagesConfig(
            bodyMessage = "Error al crear el usuario",
            isTwoButtonsAlert = IsTwoButtonsAlert(clickOnCancel = {})
        )
    )
}

enum class KindOfMessage(val color: Color) {
    SUCCESS(Success), WARING(Waring), ERROR(Danger), INFO(Info)
}

data class AlertDialogMessagesConfig(
    @param:StringRes val title: Int = R.string.title,
    val bodyMessage: String,
    @param:StringRes val buttonText: Int = R.string.aceptar,
    val kindOfMessage: KindOfMessage = KindOfMessage.INFO,
    val onConfirmation: () -> Unit = {},
    val isTwoButtonsAlert: IsTwoButtonsAlert? = null
)

data class IsTwoButtonsAlert(
    @param:StringRes val acceptText: Int = R.string.aceptar,
    val clickOnAccept: () -> Unit = {},
    @param:StringRes val cancelText: Int = R.string.cancelar_alert,
    val clickOnCancel: () -> Unit,
)

