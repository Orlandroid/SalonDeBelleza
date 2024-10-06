package com.example.citassalon.presentacion.features.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
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
    alertDialogMessagesConfig: AlertDialogMessagesConfig =
        AlertDialogMessagesConfig(
            title = R.string.title,
            bodyMessage = R.string.message_body,
            buttonText = R.string.aceptar,
        ),
    onDismissRequest: () -> Unit,
) {
    BaseCustomDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        BaseAlertDialogMessagesContent(
            onConfirmation = alertDialogMessagesConfig.onConfirmation,
            alertDialogMessagesConfig = alertDialogMessagesConfig,
        )
    }
}

@Composable
fun BaseAlertDialogMessagesContent(
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
                .wrapContentHeight(),
            text = stringResource(alertDialogMessagesConfig.bodyMessage),
            fontSize = 18.sp,
            color = AlwaysBlack,
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        AlertButton(
            modifier = Modifier,
            buttonMessage = alertDialogMessagesConfig.buttonText,
            onConfirmation = onConfirmation
        )
    }
}

@Composable
fun AlertTitle(
    kindOfMessage: KindOfMessage,
    modifier: Modifier = Modifier,
    @StringRes
    title: Int
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
fun AlertButton(
    modifier: Modifier = Modifier,
    @StringRes
    buttonMessage: Int,
    onConfirmation: () -> Unit
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onConfirmation.invoke() },
        colors = CardDefaults.cardColors(containerColor = Azul),
        shape = RectangleShape,
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
fun BaseAlertDialogPreview(
    modifier: Modifier = Modifier
) {
    BaseAlertDialogMessages(
        modifier = Modifier,
        onDismissRequest = {}
    )
}

enum class KindOfMessage(val color: Color) {
    SUCCESS(Success),
    WARING(Waring),
    ERROR(Danger),
    INFO(Info)
}

data class AlertDialogMessagesConfig(
    @StringRes
    val title: Int = R.string.title,
    @StringRes
    val bodyMessage: Int = R.string.message_body,
    @StringRes
    val buttonText: Int = R.string.aceptar,
    val kindOfMessage: KindOfMessage = KindOfMessage.INFO,
    val onConfirmation: () -> Unit = {}
)

