package com.example.citassalon.presentacion.features.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun BaseOutlinedTextField(
    text: String,
    imageVector: ImageVector = Icons.Filled.Person,
    keyboardType: KeyboardType = KeyboardType.Text,
    value: MutableState<String>,
    isEnable: Boolean = true,
    supportingText: String = "",
    showError: Boolean = false,
    isInputPassword: Boolean = false,
    clickOnIcon: () -> Unit = {}
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(
        supportingText = {
            if (showError) {
                Text(
                    text = supportingText, color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 0.dp),
        value = value.value,
        onValueChange = {
            value.value = it
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    clickOnIcon.invoke()
                    if (isInputPassword){
                        isPasswordVisible.value =  !isPasswordVisible.value
                    }
                }
            ) {
                Icon(
                    imageVector = imageVector, contentDescription = null
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