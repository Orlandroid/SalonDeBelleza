package com.example.citassalon.presentacion.features.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.citassalon.presentacion.features.components.AppProgress

@Composable
fun ProgressDialog() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            }, DialogProperties(
                dismissOnBackPress = false, dismissOnClickOutside = false
            )
        ) {
            Surface(
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                AppProgress(strokeWidth = 8.dp)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProgressDialogPreview() {
    ProgressDialog()
}