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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.citassalon.presentacion.features.extensions.dateFormat
import com.example.citassalon.presentacion.features.extensions.getCurrentDateTime
import com.example.citassalon.presentacion.features.extensions.toStringFormat
import com.example.citassalon.presentacion.features.schedule_appointment.schedule.MyDatePickerDialog
import com.example.citassalon.presentacion.features.theme.Background


@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val state = signUpViewModel.state.collectAsStateWithLifecycle()
    val uiState = signUpViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        //Todo we need to add SideEffect
    }
    BaseComposeScreenState(
        navController = navHostController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = false,
            title = stringResource(id = R.string.signUp)
        ),
        state = state.value
    ) {
        SignUpScreenContent(
            modifier = Modifier,
            uiState = uiState.value,
            onEvents = { event ->
                signUpViewModel.onEvents(event)
            }
        )
    }
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEvents: (SingUpEvents) -> Unit,
) {
    val showDatePickerDialog = remember { mutableStateOf(false) }
    val date = remember { mutableStateOf(getCurrentDateTime().toStringFormat(dateFormat)) }
    if (showDatePickerDialog.value) {
        MyDatePickerDialog(
            onDismiss = {
                showDatePickerDialog.value = false
            },
            onDateSelected = {
                date.value = it
                onEvents(SingUpEvents.OnBirthDayChange(birthday = it))
            }
        )
    }
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
            value = uiState.name,
            onValueChange = {
                onEvents(SingUpEvents.OnNameChange(name = it))
            }
        )
        InputPhone(
            value = uiState.phone,
            showError = uiState.showErrorPhone,
            onValueChange = {
                onEvents(SingUpEvents.OnPhoneChange(phone = it))
            }
        )
        InputEmail(
            value = uiState.email,
            showError = uiState.showErrorEmail,
            onValueChange = {
                onEvents(SingUpEvents.OnEmailChange(email = it))
            }
        )
        InputPassword(
            value = uiState.password,
            showError = uiState.showErrorPassword,
            onValueChange = {
                onEvents(SingUpEvents.OnPasswordChange(password = it))
            }
        )
        InputBirthday(
            value = uiState.birthday,
            clickOnIcon = {
                showDatePickerDialog.value = true
            },
            onValueChange = {
                onEvents(SingUpEvents.OnBirthDayChange(birthday = it))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonSignUp(
            isEnable = uiState.isEnableButton,
            onClick = {
                onEvents(SingUpEvents.OnSignUpClick)
            }
        )
    }
}

@Composable
private fun ButtonSignUp(
    modifier: Modifier = Modifier, isEnable: Boolean, onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = isEnable,
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
    ) {
        Text(text = stringResource(id = R.string.registrarse), color = Color.Black)
    }
}

@Composable
fun InputName(
    modifier: Modifier = Modifier, value: String, onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(modifier = modifier,
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
    BaseOutlinedTextField(modifier = modifier,
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
        })
}

@Composable
fun InputEmail(
    modifier: Modifier = Modifier,
    value: String,
    showError: Boolean,
    onValueChange: (value: String) -> Unit
) {
    BaseOutlinedTextField(modifier = modifier,
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
        })
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
    BaseOutlinedTextField(modifier = modifier,
        text = "dd/mm/aaaa",
        imageVector = Icons.Filled.Cake,
        value = value,
        isEnable = false,
        clickOnIcon = { clickOnIcon.invoke() },
        onValueChange = { onValueChange.invoke(it) })
}


@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview(modifier: Modifier = Modifier) {
    SignUpScreenContent(
        modifier = Modifier,
        uiState = SignUpUiState(),
        onEvents = {}
    )
}