package com.example.citassalon.presentacion.features.auth.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreenState
import com.example.citassalon.presentacion.features.components.BaseOutlinedTextField
import com.example.citassalon.presentacion.features.components.InputError
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.User


@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val state = signUpViewModel.state.collectAsStateWithLifecycle()
    val uiState = signUpViewModel.uiState.collectAsStateWithLifecycle()
    BaseComposeScreenState(
        navController = navHostController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            title = stringResource(id = R.string.signUp)
        ),
        state = state.value
    ) {
        SignUpScreenContent(
            modifier = Modifier, saveUserInformation = { user ->
                signUpViewModel.saveUserInformation(user)
            }, uiState = uiState.value, signUpViewModel = signUpViewModel
        )
    }
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    saveUserInformation: (user: User) -> Unit,
    uiState: SignUpUiState,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val enableButton = remember { mutableStateOf(false) }
    val birthDay = remember { mutableStateOf("") }
    Column(
        modifier
            .fillMaxSize()
            .background(Background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logoImage = painterResource(id = R.drawable.logo)
        Image(
            painter = logoImage,
            contentDescription = "SkedulyImage",
            modifier.size(150.dp)
        )
        InputName(
            value = name.value,
            onValueChange = {
                name.value = it
                signUpViewModel.validateForm()
            }
        )
        InputPhone(
            value = phone.value,
            showError = uiState.showErrorPhone,
            onValueChange = {
                phone.value = it
                signUpViewModel.validateForm()
            }
        )
        InputEmail(
            value = email.value,
            showError = uiState.showErrorEmail,
            onValueChange = {
                email.value = it
                signUpViewModel.validateForm()
            }
        )
        InputPassword(
            value = password.value,
            showError = uiState.showErrorPassword,
            onValueChange = {
                password.value = it
                signUpViewModel.validateForm()
            }
        )
        InputBirthday(
            value = birthDay.value,
            onValueChange = {
                birthDay.value = it
                signUpViewModel.validateForm()
            }
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
                signUpViewModel.sinUpV2(
                    email = email.value, password = password.value
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.registrarse), color = Color.Black)
        }
    }
}

@Composable
fun InputName(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(
        modifier = modifier,
        text = stringResource(R.string.nombre),
        value = value,
        onValueChange = { currentValue ->
            onValueChange.invoke(currentValue)
        }
    )
}

@Composable
fun InputPhone(
    modifier: Modifier = Modifier,
    value: String,
    showError: Boolean,
    onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(
        modifier = modifier,
        inputError = if (showError) {
            InputError(
                errorText = stringResource(R.string.error_phone),
            )
        } else {
            null
        },
        text = stringResource(R.string.phone),
        keyboardType = KeyboardType.Number,
        imageVector = Icons.Filled.Phone,
        value = value,
        onValueChange = {
            onValueChange.invoke(it)
        }
    )
}

@Composable
fun InputEmail(
    modifier: Modifier = Modifier,
    value: String,
    showError: Boolean,
    onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(
        modifier = modifier,
        inputError = if (showError) {
            InputError(
                errorText = stringResource(R.string.error_email),
            )
        } else {
            null
        },
        text = "example@gmail.com",
        keyboardType = KeyboardType.Email,
        imageVector = Icons.Filled.Email,
        value = value,
        onValueChange = {
            onValueChange.invoke(it)
        }
    )
}

@Composable
fun InputPassword(
    modifier: Modifier = Modifier,
    value: String,
    showError: Boolean,
    onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(
        modifier = modifier,
        inputError = if (showError) {
            InputError(
                errorText = stringResource(R.string.error_passsword),
            )
        } else {
            null
        },
        text = "password",
        keyboardType = KeyboardType.Password,
        imageVector = Icons.Filled.Lock,
        value = value,
        isInputPassword = true,
        onValueChange = {
            onValueChange.invoke(it)
        }
    )
}

@Composable
fun InputBirthday(
    modifier: Modifier = Modifier,
    value: String,
    clickOnIcon: () -> Unit = {},
    onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(
        modifier = modifier,
        text = "dd/mm/aaaa",
        imageVector = Icons.Filled.Cake,
        value = value,
        isEnable = false,
        clickOnIcon = { clickOnIcon.invoke() },
        onValueChange = { onValueChange.invoke(it) }
    )
}


@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview(modifier: Modifier = Modifier) {
    SignUpScreenContent(
        modifier = Modifier, saveUserInformation = {}, uiState = SignUpUiState()
    )
}