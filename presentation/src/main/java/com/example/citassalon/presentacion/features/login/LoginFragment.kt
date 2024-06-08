package com.example.citassalon.presentacion.features.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.ObserveSessionStatusFlow
import com.example.citassalon.presentacion.features.extensions.hideKeyboard
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.ERROR_MESSAGE
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.WARNING_MESSAGE
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.domain.state.SessionStatus
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ListeneClickOnRecoverPassword {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isSessionActive()
        configureGoogleSignIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoginScreen(viewModel)
            }
        }
    }

    @Composable
    fun LoginScreen(viewModel: LoginViewModel) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Background)
                .padding(8.dp)
                .clickable {
                    hideKeyboard()
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
                            val alert =
                                AlertDialogs(WARNING_MESSAGE, "Debes de llenar Ambos campos")
                            activity?.let { it1 ->
                                alert.show(
                                    it1.supportFragmentManager,
                                    "dialog"
                                )
                            }
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
            ObserveSessionStatusFlow(
                viewModel.loginStatus,
                "Error al iniciar session"
            ) {
                navigate(LoginFragmentDirections.actionLoginToHome32())
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable {
                    showForgetPassword()
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
                    navigate(LoginFragmentDirections.actionLoginToSignUp())
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sing_up), color = Color.Black)
            }
            Button(
                onClick = {
                    signIn()
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


    @Composable
    @Preview(showBackground = true)
    fun LoginScreenPreview() {
        LoginScreen(viewModel)
    }

    override fun setUpUi() {/*
        with(binding) {
            buttonGetIn.click {
                login()
            }
            buttonSignUp.click {
                val action = LoginFragmentDirections.actionLoginToSignUp()
                navigate(action)
            }
            txtUser.editText?.setText(viewModel.getUserEmailFromPreferences())
            tvForgetPassword.setOnClickListener {
                showForgetPassword()
            }
            container.click {
                hideKeyboard()
            }
            buttonLoginGoogle.click {
                signIn()
            }
            txtPassord.editText?.doOnTextChanged { _, _, _, _ ->
                buttonGetIn.isEnabled = areNotEmptyFields()
            }
            configureGoogleSignIn()
            isSessionActive()
            imageAnimation.setContent {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_icon))
                LottieAnimation(
                    iterations = LottieConstants.IterateForever,
                    composition = composition
                )
            }
        }
        */
    }


    private fun isSessionActive() {
        if (viewModel.isUserActive()) {
            val action = LoginFragmentDirections.actionLoginToHome32()
            navigate(action)
        }
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("619340747074-93lsb31bhcsp1nkptvkve9rlhecbclnd.apps.googleusercontent.com")
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                viewModel.firebaseAuthWithGoogle(account.idToken!!)
                Log.w("TAG", "Entramos")
            } catch (e: ApiException) {
                e.localizedMessage?.let { Log.w("TAG ERROR", it) }
            }
        }
    }

    private fun showForgetPassword() {
        val dialog = ForgetPasswordDialog(getListener())
        activity?.let { dialog.show(it.supportFragmentManager, "forgetPassword") }
    }


    private fun saveUserEmailToPreferences(user: String, isCheck: Boolean) {
        if (user.isEmpty() || !isCheck) {
            return
        }
        viewModel.saveUserEmailToPreferences(user)
    }


    override fun observerViewModel() {
        super.observerViewModel()
        observerLoginStatus()
        observerforgetPasswordStatus()
        observerGoogleLoginStatus()
    }

    private fun observerforgetPasswordStatus() {/*
        viewModel.forgetPasswordStatus.observe(viewLifecycleOwner) {
            when (it) {
                is SessionStatus.LOADING -> {
                    binding.buttonGetIn.isEnabled = false
                    binding.progress.visible()
                }

                is SessionStatus.SUCCESS -> {
                    binding.progress.invisible()
                    binding.buttonGetIn.isEnabled = true
                    showSendPasswordCorrect()
                }

                is SessionStatus.ERROR -> {
                    binding.buttonGetIn.isEnabled = true
                    binding.progress.invisible()
                }

                is SessionStatus.NETWORKERROR -> {
                    showAlertMessage(ERROR_MESSAGE, "Revisa tu conexion de internet")
                    binding.buttonGetIn.isEnabled = true
                    binding.progress.invisible()
                }
            }
        }*/


    }

    private fun observerGoogleLoginStatus() {
        viewModel.loginGoogleStatus.observe(viewLifecycleOwner) {
            when (it) {
                is SessionStatus.LOADING -> {

                }

                is SessionStatus.SUCCESS -> {
                    val action = LoginFragmentDirections.actionLoginToHome32()
                    navigate(action)
                }

                is SessionStatus.ERROR -> {
                    showAlertMessage(
                        ERROR_MESSAGE, "Error al iniciar con google intenta otro metodo"
                    )
                }

                is SessionStatus.NETWORKERROR -> {
                    showAlertMessage(ERROR_MESSAGE, "Revisa tu conexion de internet")
                }

                SessionStatus.IDLE -> {}
            }
        }
    }


    private fun observerLoginStatus() {/*
        viewModel.loginStatus.observe(viewLifecycleOwner) {
            when (it) {
                is SessionStatus.LOADING -> {
                    with(binding) {
                        progress.visible()
                        buttonGetIn.isEnabled = false
                    }
                }

                is SessionStatus.SUCCESS -> {
                    binding.progress.gone()
                    binding.buttonGetIn.isEnabled = true
                    saveUserEmailToPreferences()
                    val action = LoginFragmentDirections.actionLoginToHome32()
                    navigate(action)
                }

                is SessionStatus.ERROR -> {
                    binding.progress.gone()
                    binding.buttonGetIn.isEnabled = true
                    showAlertMessage(ERROR_MESSAGE, "Error usuario o contraseña incorrecto")
                }

                is SessionStatus.NETWORKERROR -> {
                    binding.buttonGetIn.isEnabled = true
                    binding.progress.gone()
                    showAlertMessage(ERROR_MESSAGE, "Error de internet")
                }
            }
        }*/
    }


    private fun getListener(): ListeneClickOnRecoverPassword {
        return this
    }

    private fun showAlertMessage(kindOfMessage: Int, message: String) {
        val alert = AlertDialogs(kindOfMessage, message)
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

    private fun showSendPasswordCorrect() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showSimpleMessage(
            "Informacion", "Se ha enviado un correo a tu correo para restablecer tu contraseña"
        )
    }

    private fun login() {/*
        val user = binding.txtUser.editText?.text.toString()
        val password = binding.txtPassord.editText?.text.toString()
        if (user.isNotEmpty() && password.isNotEmpty()) viewModel.login(user, password)
        else {
            val alert = AlertDialogs(WARNING_MESSAGE, "Debes de llenar Ambos campos")
            activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
        }*/
    }


    override fun clickOnResetPassword(email: String) {
        viewModel.forgetPassword(email)
    }


}