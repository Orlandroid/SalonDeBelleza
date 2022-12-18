package com.example.citassalon.ui.sign_up

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.User
import com.example.citassalon.domain.state.SessionStatus
import com.example.citassalon.databinding.SignInBinding
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.gone
import com.example.citassalon.ui.extensions.hideKeyboard
import com.example.citassalon.ui.extensions.showDatePickerDialog
import com.example.citassalon.ui.extensions.visible
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignInBinding>(R.layout.sign_in),
    DatePickerDialog.OnDateSetListener {

    private val viewModel: SignUpViewModel by viewModels()

    companion object {
        private const val MINIMAL_CHARACTERS_PASSWORD = 5
        private const val PHONE_NUMBER_CHARACTERS = 10
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbarLayout.toolbarTitle.text = "SignUp"
            buttonRegistarse.setOnClickListener {
                saveUserImformation()
                singUp()
            }
            container.setOnClickListener {
                hideKeyboard()
            }
            birtday.setEndIconOnClickListener {
                showDatePickerDialog(getListenerOnDataSet(), this@SignUpFragment)
            }
        }
        doOnTextChange()
    }

    private fun getListenerOnDataSet(): DatePickerDialog.OnDateSetListener {
        return this
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.singUp.observe(viewLifecycleOwner) {
            when (it) {
                is SessionStatus.LOADING -> {
                    with(binding) {
                        buttonRegistarse.isEnabled = false
                        progress.visible()
                    }
                }
                is SessionStatus.SUCESS -> {
                    with(binding) {
                        buttonRegistarse.isEnabled = true
                        progress.gone()
                        showDialogMessage(
                            AlertDialogs.SUCCES_MESSAGE,
                            "Usuario registraro correctament"
                        )
                        findNavController().popBackStack()
                    }
                }
                is SessionStatus.ERROR -> {
                    with(binding) {
                        buttonRegistarse.isEnabled = true
                        progress.gone()
                        showDialogMessage(
                            AlertDialogs.ERROR_MESSAGE,
                            "Error al registrar al usuario"
                        )
                    }
                }
                is SessionStatus.NETWORKERROR -> {
                    with(binding) {
                        buttonRegistarse.isEnabled = true
                        progress.gone()
                        showDialogMessage(
                            AlertDialogs.ERROR_MESSAGE,
                            "Error de red verifica que tengas conexion a internet"
                        )
                    }
                }
            }
        }
    }


    private fun showDialogMessage(kindOfMessage: Int, messageBody: String) {
        val alert = AlertDialogs(kindOfMessage, messageBody)
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

    private fun isValidPassword(): Boolean {
        val password = binding.password.editText?.text.toString()
        return password.trim().length > MINIMAL_CHARACTERS_PASSWORD
    }

    private fun getEmail(): String = binding.correo.editText?.text.toString()

    private fun isValidNumber(): Boolean =
        binding.telefono.editText?.text.toString().trim().length == PHONE_NUMBER_CHARACTERS

    private fun isTheEmailValidEmail(email: String): Boolean {
        return isValidEmail(email)
    }

    private fun areEmptyFields(): Boolean {
        with(binding) {
            val nombreIsEmpty = nombre.editText?.text.toString().trim().isEmpty()
            val telefonoIsEmpty = telefono.editText?.text.toString().trim().isEmpty()
            val correoIsEmpty = correo.editText?.text.toString().trim().isEmpty()
            val passwordIsEmpty = password.editText?.text.toString().trim().isEmpty()
            val birthDayIsEmpty = birtday.editText?.text.toString().trim().isEmpty()
            return nombreIsEmpty or telefonoIsEmpty or correoIsEmpty or passwordIsEmpty or birthDayIsEmpty
        }
    }

    private fun resetErrorsInputs() {
        with(binding) {
            password.apply {
                errorIconDrawable = null
                error = null
            }
            telefono.apply {
                errorIconDrawable = null
                error = null
            }
            correo.apply {
                errorIconDrawable = null
                error = null
            }
        }
    }

    private fun validateForm(): Boolean {
        with(binding) {
            resetErrorsInputs()
            val passwordText = password.editText?.text.toString().trim()
            val email = correo.editText?.text.toString().trim()
            val phone = telefono.editText?.text.toString().trim()
            var isValidPhone = true
            var isValidEmail = true
            var isValidPassword = true
            var areEmptyFields = false
            if (!isValidNumber()) {
                if (phone.isNotEmpty()) {
                    telefono.error = "Debes de ingresar un telefono valido"
                }
                isValidPhone = false
            }
            if (areEmptyFields()) areEmptyFields = true
            if (!isValidPassword()) {
                if (passwordText.isNotEmpty()) {
                    password.error = "La contraseña debe ser de minimo de 6 digitos"
                }
                isValidPassword = false
            }
            if (!isTheEmailValidEmail(getEmail())) {
                if (email.isNotEmpty()) {
                    correo.error = "Ingresa un correo electronico valido"
                }
                isValidEmail = false
            }
            return isValidPhone && isValidEmail && isValidPassword && !areEmptyFields
        }
    }

    private fun doOnTextChange() {
        with(binding) {
            nombre.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = validateForm()
                changeColorTextButton()
            }
            telefono.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = validateForm()
                changeColorTextButton()
            }
            correo.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = validateForm()
                changeColorTextButton()
            }
            password.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = validateForm()
                changeColorTextButton()
            }
            birtday.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = validateForm()
                changeColorTextButton()
            }
        }
    }

    private fun changeColorTextButton() {
        if (validateForm()) {
            binding.buttonRegistarse.setTextColor(android.graphics.Color.WHITE)
        }
    }

    private fun singUp() {
        val user = binding.correo.editText?.text.toString().trim()
        val password = binding.password.editText?.text.toString().trim()
        viewModel.sinUp(user, password)
    }

    private fun getUser(): User {
        with(binding) {
            val nombre = nombre.editText?.text.toString()
            val telefono = telefono.editText?.text.toString()
            val correo = correo.editText?.text.toString()
            val password = password.editText?.text.toString()
            val birthDay = birtday.editText?.text.toString()
            return User(nombre, telefono, correo, password, birthDay)
        }
    }

    private fun saveUserImformation() {
        val user = getUser()
        viewModel.saveUserImformation(user)
    }

    private fun showAlertMessage(message: String) {
        val alert = AlertsDialogMessages(requireContext())
        alert.showCustomAlert(message)
    }


    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
        binding.birtday.editText?.setText(selectedDate)
    }

}