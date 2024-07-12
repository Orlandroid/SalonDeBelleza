package com.example.citassalon.presentacion.features.login

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    focusManager.clearFocus()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(showToolbar = false)
    ) {
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
            val userEmail = viewModel.getUserEmailFromPreferences() ?: ""
            val userName = remember { mutableStateOf(userEmail) }
            val userPassword = remember { mutableStateOf("") }
            val checkedState = remember { mutableStateOf(false) }
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_icon))
            LottieAnimation(
                iterations = LottieConstants.IterateForever,
                composition = composition,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
            OutlinedTextField(value = userName.value,
                onValueChange = {
                    userName.value = it
                }, leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "person")
                }, label = {
                    Text(text = "username")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
            )
            var isPasswordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                value = userPassword.value,
                onValueChange = {
                    userPassword.value = it
                },
                leadingIcon = {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(
                            imageVector = if (isPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = "Password Visibility"
                        )
                    }
                },
                label = {
                    Text(text = "password")
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it })
                Text(text = stringResource(id = R.string.recuerda_tu_usuario))
            }
            OutlinedButton(
                onClick = {
                    viewModel.loginUi(
                        userName.value, userPassword.value,
                        onEmptyFields = {
//                            val alert =
//                                AlertDialogs(WARNING_MESSAGE, "Debes de llenar Ambos campos")
//                            activity?.let { it1 ->
//                                alert.show(
//                                    it1.supportFragmentManager,
//                                    "dialog"
//                                )
//                            }
                        }
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 25.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
//            ObserveSessionStatusFlow(
//                viewModel.loginStatus,
//                "Error al iniciar session"
//            ) {
////            navigate(LoginFragmentDirections.actionLoginToHome32())
//            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable {
//                showForgetPassword()
                },
                text = stringResource(id = R.string.olvidaste_contraseña)
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1F)
                        .background(Color.Black)
                )
            }
            Button(
                onClick = {
//                navigate(LoginFragmentDirections.actionLoginToSignUp())
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sing_up), color = Color.Black)
            }
            Button(
                onClick = {
//                signIn()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.ingresar_con_google), color = Color.Black
                )
            }
        }
    }
}