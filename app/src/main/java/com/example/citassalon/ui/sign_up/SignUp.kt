package com.example.citassalon.ui.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.databinding.SignInBinding
import com.example.citassalon.util.*
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
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.buttonRegistarse.setOnClickListener {
            singUp()
        }
        binding.container.setOnClickListener {
            hideKeyboard()
        }
        doOnTextChange()
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

    private fun isValidPassword(): Boolean =
        binding.password.editText?.text.toString().trim().length > 5

    private fun getEmail(): String = binding.correo.editText?.text.toString()

    private fun isValidTheData(): Boolean =
        !areEmptyFields() and isValidPassword() and isValidEmail(getEmail())

    private fun doOnTextChange() {
        with(binding) {
            nombre.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
            }
            telefono.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
            }
            correo.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
            }
            password.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
                if (!isValidPassword()) {
                    binding.password.editText?.error =
                        "La contraseña debe ser de minimo de 6 digitos"
                }
            }
            birtday.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
            }
        }
    }

    private fun areEmptyFields(): Boolean {
        val nombreIsEmpty = binding.nombre.editText?.text.toString().trim().isEmpty()
        val telefonoIsEmpty = binding.telefono.editText?.text.toString().trim().isEmpty()
        val correoIsEmpty = binding.correo.editText?.text.toString().trim().isEmpty()
        val passwordIsEmpty = binding.password.editText?.text.toString().trim().isEmpty()
        val birthDayIsEmpty = binding.birtday.editText?.text.toString().trim().isEmpty()
        return nombreIsEmpty or telefonoIsEmpty or correoIsEmpty or passwordIsEmpty or birthDayIsEmpty
    }

    private fun singUp() {
        val user = binding.correo.editText?.text.toString().trim()
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