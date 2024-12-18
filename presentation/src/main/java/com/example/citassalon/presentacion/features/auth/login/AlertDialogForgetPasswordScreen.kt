package com.example.citassalon.presentacion.features.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.base.SmallSpacer
import com.example.citassalon.presentacion.features.dialogs.BaseCustomDialog
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.util.isValidEmail

@Composable
fun AlertDialogForgetPasswordScreen(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    clickOnResetPassword: (email: String) -> Unit = {}
) {
    BaseCustomDialog(
        modifier = modifier,
        onDismissRequest = { }
    ) {
        AlertDialogForgetPasswordContent(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            clickOnResetPassword = clickOnResetPassword
        )
    }
}

@Composable
fun AlertDialogForgetPasswordContent(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    clickOnResetPassword: (email: String) -> Unit
) {
    val userEmail = remember { mutableStateOf("") }
    val isEnableButton = remember { mutableStateOf(false) }
    Column(
        modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Background)
        ) {
            val (imageBlock, imageClose) = createRefs()
            val logoImage = painterResource(id = R.drawable.padlock)
            val closeImage = painterResource(id = R.drawable.ic_baseline_close_24)
            Image(painter = logoImage,
                contentDescription = "ImageLock",
                modifier = Modifier.constrainAs(imageBlock) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.value(64.dp)
                    width = Dimension.value(64.dp)
                }
            )
            Image(painter = closeImage,
                contentDescription = "ImageClose",
                modifier = Modifier
                    .constrainAs(imageClose) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        height = Dimension.value(50.dp)
                        width = Dimension.value(50.dp)
                    }
                    .clickable {
                        onDismissRequest.invoke()
                    }
            )
        }
        SmallSpacer(orientation = Orientation.VERTICAL)
        Text(
            text = stringResource(id = R.string.olvidaste_contraseña),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentWidth()
        )
        InputEmail(userEmail.value) { email ->
            userEmail.value = email
            isEnableButton.value = isValidEmail(email)
        }
        SmallSpacer(orientation = Orientation.VERTICAL)
        Button(
            enabled = isEnableButton.value,
            onClick = { clickOnResetPassword.invoke(userEmail.value) }) {
            Text(text = stringResource(id = R.string.reset_password))
        }
    }
}

@Composable
fun InputEmail(
    userEmail: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        singleLine = true,
        value = userEmail,
        onValueChange = {
            onValueChange(it)
        },
        leadingIcon = {
            Icon(
                Icons.Default.Email, contentDescription = "iconEmail"
            )
        },
        label = {
            Text(text = stringResource(id = R.string.ingresa_tu_correo))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

    )
}


@Composable
@Preview(showBackground = true)
fun AlertDialogForgetPasswordPreview() {
    AlertDialogForgetPasswordScreen(onDismissRequest = {})
}