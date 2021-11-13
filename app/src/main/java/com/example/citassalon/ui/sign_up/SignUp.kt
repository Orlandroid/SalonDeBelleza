package com.example.citassalon.ui.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.databinding.SignInBinding
import com.example.citassalon.util.AlertsDialogMessages
import com.example.citassalon.util.SING_UP_TO_LOGIN
import com.example.citassalon.util.SessionStatus
import com.example.citassalon.util.navigate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUp : Fragment() {

    private var _binding: SignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelSignUp by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignInBinding.inflate(layoutInflater, container, false)
        setUpObservers()
        binding.buttonRegistarse.setOnClickListener {
            singUp()
        }
        return binding.root
    }

    private fun setUpObservers() {
        viewModel.singUp.observe(viewLifecycleOwner, {
            when (it) {
                is SessionStatus.LOADING -> {
                    binding.buttonRegistarse.isEnabled = false
                    binding.progress.visibility = View.VISIBLE
                }
                is SessionStatus.SUCESS -> {
                    binding.buttonRegistarse.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showAlertMessage("Usuario registraro correctamente")
                    navigate(SING_UP_TO_LOGIN)
                }
                is SessionStatus.ERROR -> {
                    binding.buttonRegistarse.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showAlertMessage("Error al registar al usuario")
                }
                is SessionStatus.NETWORKERROR -> {
                    binding.buttonRegistarse.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showAlertMessage("Error de red verifica que tengas conexion a internet")
                }
            }
        })
    }


    private fun singUp() {
        val user = binding.user.editText?.text.toString().trim()
        val password = binding.password.editText?.text.toString().trim()
        if (user.isNotEmpty() && password.isNotEmpty()) {
            viewModel.sinUp(user, password)
        } else {
            showAlertMessage("Debes ingresar ambos campos")
        }
    }

    private fun showAlertMessage(message: String) {
        val alert = AlertsDialogMessages(requireContext())
        alert.showCustomAlert(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}