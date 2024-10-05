package com.example.citassalon.presentacion.features.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citassalon.R
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
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector = Icons.Default.Info,
) {
    BaseCustomDialog(modifier = modifier, onDismissRequest = { }) {
        BaseAlertDialogMessagesContent()
    }
}

@Composable
fun BaseAlertDialogMessagesContent(
    modifier: Modifier = Modifier,
    title: String = "Tittle Message",
    bodyMessage: String = stringResource(R.string.test_text),
    buttonMessage: String = stringResource(R.string.aceptar)
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AlertTitle(modifier = Modifier, title = title)
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = bodyMessage,
            fontSize = 18.sp,
            color = AlwaysBlack,
        )
        AlertButton(modifier = Modifier, buttonMessage = buttonMessage)
    }
}

@Composable
fun AlertTitle(
    modifier: Modifier = Modifier, title: String
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Success),
        shape = RectangleShape,
    ) {
        SmallSpacer(orientation = Orientation.VERTICAL)
        Text(
            textAlign = TextAlign.Center,
            color = AlwaysWhite,
            fontSize = 24.sp,
            text = title,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AlertButton(
    modifier: Modifier = Modifier, buttonMessage: String
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Azul),
        shape = RectangleShape,
    ) {
        SmallSpacer(orientation = Orientation.VERTICAL)
        Text(
            textAlign = TextAlign.Center,
            color = AlwaysWhite,
            fontSize = 24.sp,
            text = buttonMessage,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
@Preview(showBackground = true)
fun BaseAlertDialogPreview(modifier: Modifier = Modifier) {
    BaseAlertDialogMessages(dialogTitle = "Alert dialog example",
        dialogText = "This is an example of an alert dialog with buttons",
        icon = Icons.Default.Info,
        onDismissRequest = {},
        onConfirmation = {})
}

enum class StatesHeaderDialog(color: Color) {
    SUCCESS(Success), WARING(Waring), ERROR(Danger), INFO(Info)
}