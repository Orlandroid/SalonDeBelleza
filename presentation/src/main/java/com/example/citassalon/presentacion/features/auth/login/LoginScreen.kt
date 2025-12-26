package com.example.citassalon.presentacion.features.auth.login

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.app_navigation.MainActivityCompose
import com.example.citassalon.presentacion.features.auth.AuthScreens
import com.example.citassalon.presentacion.features.auth.forgetpassword.ForgetPasswordDialog
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.features.theme.Danger
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    BackHandler {
        (activity as MainActivityCompose).finish()
    }
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                LoginSideEffects.NavigateToSignUp -> {
                    navController.navigate(AuthScreens.SingUpRoute)
                }

                LoginSideEffects.NavigateToHomeScreen -> {
                    navController.navigate(AppNavigationRoutes.ScheduleNavigationRoute)
                }

                LoginSideEffects.NavigateToScheduleNav -> {
                    navController.navigate(AppNavigationRoutes.ScheduleNavigationRoute)
                }

                LoginSideEffects.OnCloseFlow -> {
                    (activity as MainActivityCompose).finish()
                }
            }
        }
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false)
    ) {
        if (uiState.showDialogForgetPassword) {
            ForgetPasswordDialog(
                onDismissRequest = {
                    viewModel.onEvents(LoginEvents.OnCloseDialogForgetPassword)
                }
            )
        }
        if (uiState.showDialogPasswordOrEmailWrong) {
            val config = AlertDialogMessagesConfig(
                kindOfMessage = KindOfMessage.ERROR,
                title = R.string.error,
                bodyMessage = "Error invalid user or password",
                onConfirmation = {
                    viewModel.onEvents(LoginEvents.OnCloseErrorDialog)
                }
            )
            BaseAlertDialogMessages(alertDialogMessagesConfig = config)
        }
        LoginScreenContent(
            uiState = uiState,
            onEvents = { event ->
                viewModel.onEvents(event)
            },
            clearFocus = {
                focusManager.clearFocus()
            }
        )
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvents: (LoginEvents) -> Unit,
    clearFocus: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(8.dp)
            .clickable {
                clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLogin()
        InputUserName(
            userName = uiState.userName,
            showErrorUserName = uiState.showErrorUserName,
            onEvents = onEvents
        )
        InputPassword(
            userPassword = uiState.password,
            isPasswordVisible = uiState.showPassword,
            showPasswordError = uiState.showErrorPassword,
            onEvents = onEvents
        )
        CheckRememberUser(
            checkedState = uiState.rememberUserName,
            onEvents = onEvents
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(
            isEnableButton = uiState.isButtonLoginEnable,
            onEvents = onEvents,
            isLoading = uiState.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.clickable {
                onEvents(LoginEvents.OnForgetPasswordClick)
            },
            text = stringResource(id = R.string.olvidaste_contraseña)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextOr()
        SignUpButton(onClick = { onEvents(LoginEvents.GoToSignUpScreen) })
        GoogleButton()
    }
}

@Composable
private fun ImageLogin() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_icon))
    LottieAnimation(
        enableMergePaths = true,
        iterations = LottieConstants.IterateForever,
        composition = composition,
        modifier = Modifier.size(150.dp)
    )
}

@Composable
private fun InputUserName(
    modifier: Modifier = Modifier,
    userName: String,
    showErrorUserName: Boolean,
    onEvents: (LoginEvents) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp),
        value = userName,
        onValueChange = {
            onEvents(LoginEvents.OnUserNameChange(name = it))
        },
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = "person")
        },
        label = {
            Text(text = stringResource(R.string.username))
        },
        supportingText = {
            if (showErrorUserName) {
                Text(text = stringResource(R.string.error_email), color = Danger)
            }
        },
        singleLine = true
    )
}

@Composable
private fun InputPassword(
    modifier: Modifier = Modifier,
    userPassword: String,
    isPasswordVisible: Boolean,
    showPasswordError: Boolean,
    onEvents: (LoginEvents) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp),
        value = userPassword,
        onValueChange = { mPassword ->
            onEvents(LoginEvents.OnPasswordChange(password = mPassword))
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    onEvents(LoginEvents.OnShowPassword(show = !isPasswordVisible))
                }
            ) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff,
                    contentDescription = "Password Visibility"
                )
            }
        },
        label = {
            Text(text = "password")
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        supportingText = {
            if (showPasswordError) {
                Text(text = stringResource(R.string.error_passsword), color = Danger)
            }
        },
        singleLine = true
    )
}


@Composable
private fun CheckRememberUser(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    onEvents: (LoginEvents) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                onEvents(LoginEvents.OnRememberUserChecker(it))
            }
        )
        Text(text = stringResource(id = R.string.recuerda_tu_usuario))
    }
}

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    isEnableButton: Boolean,
    isLoading: Boolean,
    onEvents: (LoginEvents) -> Unit
) {
    OutlinedButton(
        enabled = isEnableButton, onClick = {
            onEvents(LoginEvents.OnLoginClick)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 25.dp, 0.dp, 0.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(Modifier.size(40.dp))
        } else {
            Text(
                text = stringResource(R.string.log_in),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun TextOr() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .weight(1F)
                .background(Color.Black)
        )
        Text(text = "OR", Modifier.padding(horizontal = 16.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .weight(1F)
                .background(Color.Black)
        )
    }
}

@Composable
private fun SignUpButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.sing_up), color = Color.Black
        )
    }
}

@Composable
private fun GoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ingresar_con_google),
            color = Color.Black
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenContentPreview() {
    LoginScreenContent(uiState = LoginUiState(), onEvents = {}, clearFocus = {})
}