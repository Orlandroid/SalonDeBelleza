package com.example.citassalon.presentacion.features.auth.login

import android.app.Activity
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.app_navigation.MainActivityCompose
import com.example.citassalon.presentacion.features.auth.AuthScreens
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogForgetPasswordScreen
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToScheduleNav: () -> Unit
) {
    //val user and pass android@gmail.com android1234
    val status = viewModel.status.collectAsState()
    val effect = viewModel.effects.collectAsState()
    LoginScreenContent(
        userEmail = viewModel.getUserEmailFromPreferences(),
        navController = navController,
        loginUiState = status.value,
        loginAction = viewModel::handleActions,
        isLoading = status.value.isLoading
    )
    LaunchedEffect(effect.value) {
        when (effect.value) {
            LoginUiEffect.GoToSignUp -> {

            }

            LoginUiEffect.Idle -> {

            }

            LoginUiEffect.NavigateToHomeScreen -> {
                navigateToScheduleNav.invoke()
            }
        }
    }
}

@Composable
fun LoginScreenContent(
    isLoading: Boolean,
    userEmail: String? = null,
    navController: NavController,
    loginUiState: LoginUiState,
    loginAction: (LoginActions) -> Unit
) {
    val focusManager = LocalFocusManager.current
    focusManager.clearFocus()
    val activity = LocalContext.current as Activity
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false),
        isLoading = isLoading
    ) {
        BackHandler {
            (activity as MainActivityCompose).finish()
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(Background)
                .padding(8.dp)
                .clickable {
                    focusManager.clearFocus()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val userName = remember { mutableStateOf(userEmail ?: "") }
            val userPassword = remember { mutableStateOf("") }
            val checkedState = remember { mutableStateOf(false) }
            val isPasswordVisible = remember { mutableStateOf(false) }
            val showDialogForgetPassword = remember { mutableStateOf(false) }
            val isEnableLoginButton = remember { mutableStateOf(true) }
            LottieAnimation()
            UserName(userName = userName)
            Password(userPassword = userPassword, isPasswordVisible = isPasswordVisible)
            CheckRememberUser(checkedState = checkedState)
            Spacer(modifier = Modifier.height(16.dp))
            LoginButton(
                isEnable = isEnableLoginButton.value,
                user = userName.value,
                password = userPassword.value,
                loginAction = loginAction
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable {
                    showDialogForgetPassword.value = true
                },
                text = stringResource(id = R.string.olvidaste_contraseña)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextOr()
            SignUpButton(
                onClick = {
                    navController.navigate(AuthScreens.SingUpRoute)
                }
            )
            GoogleButton()
            if (showDialogForgetPassword.value) {
                AlertDialogForgetPasswordScreen(
                    onDismissRequest = {
                        showDialogForgetPassword.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun LottieAnimation(modifier: Modifier = Modifier) {
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
fun UserName(modifier: Modifier = Modifier, userName: MutableState<String>) {
    OutlinedTextField(modifier = modifier
        .fillMaxWidth()
        .padding(0.dp, 20.dp, 0.dp, 0.dp),
        value = userName.value,
        onValueChange = {
            userName.value = it
            Log.w("DEBUG", userName.value)
        },
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = "person")
        },
        label = {
            Text(text = "username")
        }
    )
}

@Composable
fun Password(
    modifier: Modifier = Modifier,
    userPassword: MutableState<String>,
    isPasswordVisible: MutableState<Boolean>
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp),
        value = userPassword.value,
        onValueChange = {
            userPassword.value = it
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    isPasswordVisible.value = !isPasswordVisible.value
                }
            ) {
                Icon(
                    imageVector = if (isPasswordVisible.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff, contentDescription = "Password Visibility"
                )
            }
        },
        label = {
            Text(text = "password")
        },
        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None
        else PasswordVisualTransformation(),
    )
}


@Composable
fun CheckRememberUser(
    modifier: Modifier = Modifier,
    checkedState: MutableState<Boolean>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(checked = checkedState.value,
            onCheckedChange = { checkedState.value = it })
        Text(text = stringResource(id = R.string.recuerda_tu_usuario))
    }
}

@Composable
fun LoginButton(
    isEnable: Boolean,
    modifier: Modifier = Modifier,
    user: String,
    password: String,
    loginAction: (LoginActions) -> Unit,
) {
    val clickOnLogin = {
        loginAction(
            LoginActions.Login(
                email = user, password = password
            )
        )
    }
    OutlinedButton(
        enabled = isEnable,
        onClick = {
            if (password.isNotEmpty() or user.isNotEmpty()) {
                clickOnLogin.invoke()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 25.dp, 0.dp, 0.dp)
    ) {
        Text(
            text = "Ingresar",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
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
        Text(text = stringResource(id = R.string.sing_up), color = Color.Black)
    }
}

@Composable
fun GoogleButton(
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
            text = stringResource(id = R.string.ingresar_con_google), color = Color.Black
        )
    }
}