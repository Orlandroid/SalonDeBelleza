package com.example.citassalon.ui.login

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.databinding.FragmentLoginBinding
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class Login : Fragment(), ListeneClickOnRecoverPassword {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelLogin by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpUi()
        setUpObserves()
        return binding.root
    }

    private fun setUpUi() {
        binding.buttonGetIn.setOnClickListener {
            login()
        }
        binding.buttonSignUp.setOnClickListener {
            navigate(LOGIN_TO_SINGUP)
        }
        binding.txtUser.addOnEditTextAttachedListener {
            animationImage()
        }
        binding.txtUser.editText?.setText(viewModel.getUserEmailFromPreferences())
        binding.tvForgetPassword.setOnClickListener {
            showForgetPassword()
        }
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun showForgetPassword() {
        val dialog = ForgetPasswordDialog(getListener())
        activity?.let { dialog.show(it.supportFragmentManager, "forgetPassword") }
    }

    private fun animationImage() {
        binding.appCompatImageView.animate().apply {
            val valueAnimator = ValueAnimator.ofFloat(0f, 360f)
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                binding.appCompatImageView.rotation = value
            }
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = 2000
            valueAnimator.start()
        }
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
                    navigate(LOGIN_TO_HOME)
                    saveUserEmailToPreferences()
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