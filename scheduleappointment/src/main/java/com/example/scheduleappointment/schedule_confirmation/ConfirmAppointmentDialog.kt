package com.example.scheduleappointment.schedule_confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.scheduleappointment.R
import com.example.core.ui.base.LongSpacer
import com.example.core.ui.base.Orientation
import com.example.core.ui.dialogs.BaseCustomDialog
import com.example.core.ui.theme.BtnNext
import com.example.core.ui.theme.Cafe

@Composable
fun ConfirmAppointmentDialog(
    modifier: Modifier = Modifier,
    clickOnAccept: () -> Unit,
    clickOnCancel: () -> Unit
) {
    BaseCustomDialog(
        modifier = modifier,
        onDismissRequest = { }
    ) {
        ConfirmAppointmentDialogContent(
            clickOnAccept = clickOnAccept,
            clickOnCancel = clickOnCancel
        )
    }
}

@Composable
private fun ConfirmAppointmentDialogContent(
    modifier: Modifier = Modifier,
    clickOnCancel: () -> Unit,
    clickOnAccept: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LongSpacer(orientation = Orientation.VERTICAL)
        Text(text = stringResource(R.string.mensaje_confirmacion), fontSize = 24.sp)
        LongSpacer(orientation = Orientation.VERTICAL)
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = BtnNext),
            onClick = clickOnAccept
        ) {
            Text(text = stringResource(R.string.confirmar))
        }
        LongSpacer(orientation = Orientation.VERTICAL)
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Cafe),
            onClick = clickOnCancel
        ) {
            Text(stringResource(R.string.cancelar))
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ConfirmAppointmentDialogPreview() {
    ConfirmAppointmentDialog(
        clickOnCancel = {},
        clickOnAccept = {}
    )
}

