package com.example.citassalon.presentacion.features.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun BaseOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    imageVector: ImageVector = Icons.Filled.Person,
    keyboardType: KeyboardType = KeyboardType.Text,
    value: String,
    isEnable: Boolean = true,
    inputError: InputError? = null,
    isInputPassword: Boolean = false,
    onValueChange: (value: String) -> Unit = {},
    clickOnIcon: () -> Unit = {}
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(
        supportingText = {
            if (inputError != null) {
                Text(
                    text = inputError.errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 0.dp),
        value = value,
        onValueChange = { mText ->
            onValueChange.invoke(mText)
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    clickOnIcon.invoke()
                    if (isInputPassword) {
                        isPasswordVisible.value = !isPasswordVisible.value
                    }
                }
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null
                )
            }
        },
        label = {
            Text(text = text)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = isEnable,
        visualTransformation = if (isInputPassword && isPasswordVisible.value) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}


@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true)
fun BaseOutlinedTextFieldPreview(modifier: Modifier = Modifier) {
    BaseOutlinedTextField(
        modifier = Modifier.padding(8.dp),
        text = "Email",
        value = "android@gmail.com"
    )
}

data class InputError(
    val errorText: String = "Error",
)
