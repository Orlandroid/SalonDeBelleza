package com.example.citassalon.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentLoginBinding
import com.example.citassalon.util.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : Fragment(), ListeneClickOnRecoverPassword {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelLogin by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 200


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpUi()
        setUpObserves()
        configureGoogleSignIn()
        isSessionActive()
        return binding.root
    }


    private fun isSessionActive() {
        Log.w("ANDROID", viewModel.getUserSession().toString())
        if (viewModel.getUserSession()) {
            navigate(LOGIN_TO_HOME)
        }
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("691057184844-0qj57lspkfavo5cckmmhib5725dg8jgl.apps.googleusercontent.com")
            .requestEmail()
            .build()
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
                Log.w("TAG", e.localizedMessage)
            }
        }
    }


    private fun setUpUi() {
        binding.buttonGetIn.setOnClickListener {
            login()
        }
        binding.buttonSignUp.setOnClickListener {
            navigate(LOGIN_TO_SINGUP)
        }
        binding.txtUser.editText?.setText(viewModel.getUserEmailFromPreferences())
        binding.tvForgetPassword.setOnClickListener {
            showForgetPassword()
        }
        binding.container.setOnClickListener {
            hideKeyboard()
        }
        binding.buttonLoginGoogle.setOnClickListener {
            signIn()
        }
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


    private fun setUpObserves() {
        observerLoginStatus()
        observerforgetPasswordStatus()
        observerGoogleLoginStatus()
    }


    private fun observerforgetPasswordStatus() {
        viewModel.forgetPasswordStatus.observe(viewLifecycleOwner, {
            when (it) {
                is SessionStatus.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is SessionStatus.SUCESS -> {
                    binding.progress.visibility = View.INVISIBLE
                    showSendPasswordCorrect()
                }
                is SessionStatus.ERROR -> {

                }
                is SessionStatus.NETWORKERROR -> {
                    showAlertMessage("Revisa tu conexion de internet")
                }
            }
        })
    }

    private fun observerGoogleLoginStatus() {
        viewModel.loginGoogleStatus.observe(viewLifecycleOwner, {
            when (it) {
                is SessionStatus.LOADING -> {

                }
                is SessionStatus.SUCESS -> {
                    val action = LoginDirections.actionLoginToHome32()
                    navigate(action)
                }
                is SessionStatus.ERROR -> {
                    showAlertMessage("Error al iniciar con google intenta otro metodo")
                }
                is SessionStatus.NETWORKERROR -> {
                    showAlertMessage("Revisa tu conexion")
                }
            }
        })
    }


    private fun observerLoginStatus() {
        viewModel.loginStatus.observe(viewLifecycleOwner, {
            when (it) {
                is SessionStatus.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.buttonGetIn.isEnabled = false
                }
                is SessionStatus.SUCESS -> {
                    binding.progress.visibility = View.GONE
                    binding.buttonGetIn.isEnabled = true
                    saveUserEmailToPreferences()
                    navigate(LOGIN_TO_HOME)
                }
                is SessionStatus.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.buttonGetIn.isEnabled = true
                    showAlertMessage("Error al iniciar session con el usuario")
                }
                is SessionStatus.NETWORKERROR -> {
                    binding.buttonGetIn.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showAlertMessage("Error de internet")
                }
            }
        })
    }


    private fun getListener(): ListeneClickOnRecoverPassword {
        return this
    }

    private fun showAlertMessage(message: String) {
        val alert = AlertsDialogMessages(requireContext())
        alert.showCustomAlert(message)
    }

    private fun showSendPasswordCorrect() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showSimpleMessage(
            "Informacion",
            "Se ha enviado un correo a tu correo para restablecer tu contraseña"
        )
    }

    private fun login() {
        val user = binding.txtUser.editText?.text.toString()
        val password = binding.txtPassord.editText?.text.toString()
        if (user.isNotEmpty() && password.isNotEmpty())
            viewModel.login(user, password)
        else showAlertMessage("Debes de llenar ambos campos")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clickOnResetPassword(email: String) {
        viewModel.forgetPassword(email)
    }


}