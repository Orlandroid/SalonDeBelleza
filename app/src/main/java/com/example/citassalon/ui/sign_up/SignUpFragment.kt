package com.example.citassalon.ui.sign_up

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.data.models.User
import com.example.citassalon.data.state.SessionStatus
import com.example.citassalon.databinding.SignInBinding
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: SignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelSignUp by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignInBinding.inflate(layoutInflater, container, false)
        setUpObservers()
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
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

    private fun setUpObservers() {
        viewModel.singUp.observe(viewLifecycleOwner) {
            when (it) {
                is SessionStatus.LOADING -> {
                    binding.buttonRegistarse.isEnabled = false
                    binding.progress.visibility = View.VISIBLE
                }
                is SessionStatus.SUCESS -> {
                    binding.buttonRegistarse.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showDialogMessage(AlertDialogs.SUCCES_MESSAGE,"Usuario registraro correctament")
                    findNavController().popBackStack()
                }
                is SessionStatus.ERROR -> {
                    binding.buttonRegistarse.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showDialogMessage(AlertDialogs.ERROR_MESSAGE,"Error al registrar al usuario")
                }
                is SessionStatus.NETWORKERROR -> {
                    binding.buttonRegistarse.isEnabled = true
                    binding.progress.visibility = View.GONE
                    showDialogMessage(AlertDialogs.ERROR_MESSAGE,"Error de red verifica que tengas conexion a internet")
                }
            }
        }
    }

    private fun showDialogMessage(kindOfMessage:Int,messageBody:String){
        val alert = AlertDialogs(kindOfMessage,messageBody)
        activity?.let { it1 -> alert.show(it1.supportFragmentManager,"dialog") }
    }

    private fun isValidPassword(): Boolean =
        binding.password.editText?.text.toString().trim().length > 5

    private fun getEmail(): String = binding.correo.editText?.text.toString()

    private fun isValidTheData(): Boolean =
        !areEmptyFields() and isValidPassword() and isTheEmailValidEmail(getEmail())

    private fun doOnTextChange() {
        with(binding) {
            nombre.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
                changeColorTextButton()
            }
            telefono.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
                changeColorTextButton()
            }
            correo.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
                changeColorTextButton()
            }
            password.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
                if (!isValidPassword()) {
                    binding.password.editText?.error =
                        "La contraseña debe ser de minimo de 6 digitos"
                }
                changeColorTextButton()
            }
            birtday.editText?.doOnTextChanged { _, _, _, _ ->
                buttonRegistarse.isEnabled = isValidTheData()
                changeColorTextButton()
            }
        }
    }

    private fun changeColorTextButton(){
        if (isValidTheData()){
            binding.buttonRegistarse.setTextColor(android.graphics.Color.WHITE)
        }
    }


    private fun isTheEmailValidEmail(email:String):Boolean{
        if (isValidEmail(email)){
            return true
        }
        binding.correo.editText?.error = "Debes de ingresar un correo valido"
        return false
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
        viewModel.sinUp(user, password)
    }

    private fun getUser(): User {
        val nombre = binding.nombre.editText?.text.toString()
        val telefono = binding.telefono.editText?.text.toString()
        val correo = binding.correo.editText?.text.toString()
        val password = binding.password.editText?.text.toString()
        val birthDay = binding.birtday.editText?.text.toString()
        return User(nombre, telefono, correo, password, birthDay)
    }

    private fun saveUserImformation() {
        val user = getUser()
        viewModel.saveUserImformation(user)
    }

    private fun showAlertMessage(message: String) {
        val alert = AlertsDialogMessages(requireContext())
        alert.showCustomAlert(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
        binding.birtday.editText?.setText(selectedDate)
    }

}