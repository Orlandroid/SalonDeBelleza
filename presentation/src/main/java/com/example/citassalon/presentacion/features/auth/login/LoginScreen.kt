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
import androidx.compose.foundation.layout.width
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
import com.example.citassalon.presentacion.features.app_navigation.MainActivityCompose
import com.example.citassalon.presentacion.features.base.BaseComposeScreenState
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToScheduleNav: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {
    val activity = LocalContext.current as Activity///this kills compose preview
    BackHandler {
        (activity as MainActivityCompose).finish()
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.loginSideEffects.collect { effect ->
            when (effect) {
                LoginSideEffects.GoToSignUp -> {
                    navigateToSignUpScreen.invoke()
                }

                LoginSideEffects.NavigateToHomeScreen -> {
                    navigateToScheduleNav.invoke()
                }
            }
        }
    }
    BaseComposeScreenState(
        alertDialogMessagesConfig = AlertDialogMessagesConfig(
            bodyMessage = stringResource(R.string.error_password_or_user)
        ),
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false),
        state = state.value
    ) {

    }
    LoginScreenContent(
        uiState = uiState.value,
        onEvents = { event ->
            viewModel.onEvents(event)
        }
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvents: (LoginEvents) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier
            .fillMaxSize()
            .background(Background)
            .padding(8.dp)
            .clickable {
                focusManager.clearFocus()
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation()
        UserName(userName = uiState.userName, onValueChange = { userName ->
            onEvents(LoginEvents.OnUserNameChange(name = userName))
        })
        Password(userPassword = uiState.password,
            isPasswordVisible = uiState.showPassword,
            onShowPasswordClick = { showPassword ->
                onEvents(LoginEvents.OnShowPassword(show = showPassword))
            },
            onValueChange = { mPassword ->
                onEvents(LoginEvents.OnPasswordChange(password = mPassword))
            })
        CheckRememberUser(checkedState = uiState.rememberUserName, onCheckedChange = { isCheck ->
            onEvents(LoginEvents.OnRememberUserChecker(isCheck = isCheck))
        })
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(
            isEnableButton = uiState.isButtonLoginEnable, onClick = {
                onEvents(LoginEvents.OnLoginClick)
            }, isLoading = uiState.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.clickable {
                onEvents(LoginEvents.OnForgetPasswordClick)
            }, text = stringResource(id = R.string.olvidaste_contraseña)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextOr()
        SignUpButton {
            onEvents(LoginEvents.GoToSignUpScreen)
        }
        GoogleButton()
        if (uiState.showDialogForgetPassword) {
            AlertDialogForgetPasswordScreen(onDismissRequest = {
                onEvents(LoginEvents.OnCloseDialogForgetPassword)
            }, clickOnResetPassword = { mEmail ->
                onEvents(LoginEvents.OnResetPassword(email = mEmail))
            })
        }
    }
}

@Composable
private fun LottieAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_icon))
    LottieAnimation(
        iterations = LottieConstants.IterateForever,
        composition = composition,
        modifier = modifier
            .height(150.dp)
            .width(150.dp)
    )
}

@Composable
fun UserName(
    modifier: Modifier = Modifier,
    userName: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(modifier = modifier
        .fillMaxWidth()
        .padding(0.dp, 20.dp, 0.dp, 0.dp),
        value = userName,
        onValueChange = {
            onValueChange.invoke(it)
        },
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = "person")
        },
        label = {
            Text(text = stringResource(R.string.username))
        })
}

@Composable
fun Password(
    modifier: Modifier = Modifier,
    userPassword: String,
    isPasswordVisible: Boolean,
    onShowPasswordClick: (show: Boolean) -> Unit,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp),
        value = userPassword,
        onValueChange = { mPassword ->
            onValueChange.invoke(mPassword)
        },
        leadingIcon = {
            IconButton(onClick = {
                onShowPasswordClick(!isPasswordVisible)
            }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff, contentDescription = "Password Visibility"
                )
            }
        },
        label = {
            Text(text = "password")
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
    )
}


@Composable
fun CheckRememberUser(
    modifier: Modifier = Modifier, checkedState: Boolean, onCheckedChange: (check: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(checked = checkedState, onCheckedChange = {
            onCheckedChange(it)
        })
        Text(text = stringResource(id = R.string.recuerda_tu_usuario))
    }
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier, isEnableButton: Boolean, isLoading: Boolean, onClick: () -> Unit
) {
    OutlinedButton(
        enabled = isEnableButton, onClick = {
            onClick.invoke()
        }, modifier = modifier
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
private fun TextOr(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .weight(1F)
                .background(Color.Black)
        )
        Text(text = "OR", Modifier.padding(horizontal = 16.dp))
        Spacer(
            modifier = modifier
                .height(1.dp)
                .weight(1F)
                .background(Color.Black)
        )
    }
}

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.sing_up), color = Color.Black)
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.ingresar_con_google), color = Color.Black
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenContentPreview(modifier: Modifier = Modifier) {
    LoginScreenContent(uiState = LoginUiState(), onEvents = {})
}