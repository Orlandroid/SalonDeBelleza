package com.example.citassalon.presentacion.features.login

import android.content.Intent
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentLoginBinding
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.ERROR_MESSAGE
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.WARNING_MESSAGE
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.gone
import com.example.citassalon.presentacion.features.extensions.hideKeyboard
import com.example.citassalon.presentacion.features.extensions.invisible
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.visible
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.domain.state.SessionStatus
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login),
    ListeneClickOnRecoverPassword {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 200
    }

    override fun setUpUi() {
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
            txtPassord.editText?.doOnTextChanged { text, start, before, count ->
                buttonGetIn.isEnabled = areNotEmptyFields()
            }
            configureGoogleSignIn()
            isSessionActive()
        }
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


    private fun areNotEmptyFields(): Boolean {
        val user = binding.txtUser.editText?.text.toString().trim()
        val password = binding.txtPassord.editText?.text.toString().trim()
        if (user.isNotEmpty() && password.isNotEmpty()) {
            return password.length > 8
        }
        return false
    }

    private fun showForgetPassword() {
        val dialog = ForgetPasswordDialog(getListener())
        activity?.let { dialog.show(it.supportFragmentManager, "forgetPassword") }
    }


    private fun saveUserEmailToPreferences() {
        val userEmail = binding.txtUser.editText?.text.toString()
        if (userEmail.isEmpty()) {
            return
        }
        if (!binding.checkBox.isChecked) {
            return
        }
        viewModel.saveUserEmailToPreferences(userEmail)
    }


    override fun observerViewModel() {
        super.observerViewModel()
        observerLoginStatus()
        observerforgetPasswordStatus()
        observerGoogleLoginStatus()
    }

    private fun observerforgetPasswordStatus() {
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
        }
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
            }
        }
    }


    private fun observerLoginStatus() {
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
        }
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

    private fun login() {
        val user = binding.txtUser.editText?.text.toString()
        val password = binding.txtPassord.editText?.text.toString()
        if (user.isNotEmpty() && password.isNotEmpty()) viewModel.login(user, password)
        else {
            val alert = AlertDialogs(WARNING_MESSAGE, "Debes de llenar Ambos campos")
            activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
        }
    }


    override fun clickOnResetPassword(email: String) {
        viewModel.forgetPassword(email)
    }


}