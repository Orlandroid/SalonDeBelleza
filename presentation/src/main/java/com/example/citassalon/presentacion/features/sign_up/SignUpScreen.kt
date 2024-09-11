package com.example.citassalon.presentacion.features.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.BaseOutlinedTextField
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.User


@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    BaseComposeScreen(
        navController = navHostController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            title = stringResource(id = R.string.signUp)
        )
    ) {
        SignUpScreenContent(
            modifier = Modifier,
            saveUserInformation = { user ->
                signUpViewModel.saveUserInformation(user)
            },
            sinUp = { email, password ->
                signUpViewModel.sinUp(email = email, password = password)
            }
        )
    }
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    saveUserInformation: (user: User) -> Unit,
    sinUp: (email: String, password: String) -> Unit,
) {
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val enableButton = remember { mutableStateOf(false) }
    val birthDay = remember { mutableStateOf("") }
    val errorPhone = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }
    val errorPassword = remember { mutableStateOf(false) }
    Column(
        modifier
            .fillMaxSize()
            .background(Background)
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseOutlinedTextField(text = "Nombre", value = name)
        BaseOutlinedTextField(
            showError = errorPhone.value,
            supportingText = "Debes de ingresar un telefono valido",
            text = "Telefono",
            keyboardType =
            KeyboardType.Number,
            imageVector = Icons.Filled.Phone,
            value = phone
        )
        BaseOutlinedTextField(
            showError = errorEmail.value,
            supportingText = "Ingresa un correo electronico valido",
            text = "example@gmail.com",
            keyboardType = KeyboardType.Email,
            imageVector = Icons.Filled.Email,
            value = email
        )
        BaseOutlinedTextField(
            showError = errorPassword.value,
            supportingText = "La contraseña debe ser de minimo de 6 digitos",
            text = "password",
            keyboardType = KeyboardType.Password,
            imageVector = Icons.Filled.Lock,
            value = password,
            isInputPassword = true
        )
        BaseOutlinedTextField(
            text = "dd/mm/aaaa",
            imageVector = Icons.Filled.Cake,
            value = birthDay,
            isEnable = false,
            clickOnIcon = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = enableButton.value,
            onClick = {
                saveUserInformation.invoke(
                    User(
                        name = name.value,
                        phone = phone.value,
                        email = email.value,
                        password = password.value,
                        birthDay = birthDay.value
                    )
                )
                sinUp(email.value, password.value)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.registrarse), color = Color.Black)
        }
    }
}