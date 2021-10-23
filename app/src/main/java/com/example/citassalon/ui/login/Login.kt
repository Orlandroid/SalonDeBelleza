package com.example.citassalon.ui.login

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.databinding.FragmentLoginBinding
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelLogin by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.buttonGetIn.setOnClickListener {
            login()
        }
        binding.buttonSignUp.setOnClickListener {
            it.navigate(LOGIN_TO_SINGUP)
        }
        binding.txtUser.addOnEditTextAttachedListener {
            animationImage()
        }
        setUpObserves()
        return binding.root
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

    private fun setUpObserves() {
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

    private fun showAlertMessage(message: String) {
        val alert = AlertsDialogMessages(requireContext())
        alert.showCustomAlert(message)
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

}