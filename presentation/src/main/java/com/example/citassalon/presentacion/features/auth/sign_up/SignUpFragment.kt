package com.example.citassalon.presentacion.features.auth.sign_up

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.ObserveSessionStatusLiveData
import com.example.citassalon.presentacion.features.extensions.showDatePickerDialog
import com.example.citassalon.presentacion.features.extensions.showProgress
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.util.isValidEmail
import com.example.domain.entities.remote.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    DatePickerDialog.OnDateSetListener {

    private val viewModel: SignUpViewModel by viewModels()
    private var birthDay: MutableState<String> = mutableStateOf("")
    private var name: MutableState<String> = mutableStateOf("")
    private var phone: MutableState<String> = mutableStateOf("")
    private var email: MutableState<String> = mutableStateOf("")
    private var password: MutableState<String> = mutableStateOf("")
    private var enableButton: MutableState<Boolean> = mutableStateOf(false)
    private var errorPhone: MutableState<Boolean> = mutableStateOf(false)
    private var errorEmail: MutableState<Boolean> = mutableStateOf(false)
    private var errorPassword: MutableState<Boolean> = mutableStateOf(false)

    companion object {
        private const val MINIMAL_CHARACTERS_PASSWORD = 5
        private const val PHONE_NUMBER_CHARACTERS = 10
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.signUp)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SignUpScreen()
            }
        }
    }

    @Composable
    fun SignUpScreen() {
        val singUp = viewModel.singUp.observeAsState()
        name = remember { mutableStateOf("") }
        phone = remember { mutableStateOf("") }
        email = remember { mutableStateOf("") }
        password = remember { mutableStateOf("") }
        enableButton = remember { mutableStateOf(false) }
        birthDay = remember { mutableStateOf("") }
        errorPhone = remember { mutableStateOf(false) }
        errorEmail = remember { mutableStateOf(false) }
        errorPassword = remember { mutableStateOf(false) }
        Column(
            Modifier
                .fillMaxSize()
                .background(Background)
                .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ObserveSessionStatusLiveData(
                messageOnError = "Error al registrar al usuario",
                state = singUp,
            ) {
                showDialogMessage(
                    AlertDialogs.SUCCESS_MESSAGE, "Usuario registraro correctament"
                )
                findNavController().popBackStack()
            }
            OutlinedTextField(text = "Nombre", value = name)
            OutlinedTextField(
                showError = errorPhone.value,
                supportingText = "Debes de ingresar un telefono valido",
                text = "Telefono",
                keyboardType =
                KeyboardType.Number,
                imageVector = Icons.Filled.Phone,
                value = phone
            )
            OutlinedTextField(
                showError = errorEmail.value,
                supportingText = "Ingresa un correo electronico valido",
                text = "example@gmail.com",
                keyboardType = KeyboardType.Email,
                imageVector = Icons.Filled.Email,
                value = email
            )
            OutlinedTextField(
                showError = errorPassword.value,
                supportingText = "La contraseña debe ser de minimo de 6 digitos",
                text = "password",
                keyboardType = KeyboardType.Password,
                imageVector = Icons.Filled.Lock,
                value = password
            )
            OutlinedTextField(
                text = "dd/mm/aaaa",
                imageVector = Icons.Filled.Cake,
                value = birthDay,
                isEnable = false,
                clickOnIcon = { showDatePickerDialog(getListenerOnDataSet(), this@SignUpFragment) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = enableButton.value,
                onClick = {
                    viewModel.saveUserInformation(
                        user = getUser()
                    )
                    viewModel.sinUp(email.value, password.value)
                    showProgress()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.registrarse), color = Color.Black)
            }
        }
    }

    @Composable
    fun OutlinedTextField(
        text: String,
        imageVector: ImageVector = Icons.Filled.Person,
        keyboardType: KeyboardType = KeyboardType.Text,
        value: MutableState<String>,
        isEnable: Boolean = true,
        supportingText: String = "",
        showError: Boolean = false,
        clickOnIcon: () -> Unit = {}
    ) {
        OutlinedTextField(
            supportingText = {
                if (showError) {
                    Text(
                        text = supportingText, color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 16.dp, 0.dp, 0.dp),
            value = value.value,
            onValueChange = {
                doOnTextChange()
                value.value = it
            },
            leadingIcon = {
                IconButton(
                    onClick = { clickOnIcon.invoke() }
                ) {
                    Icon(
                        imageVector = imageVector, contentDescription = null
                    )
                }
            },
            label = {
                Text(text = text)
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            enabled = isEnable
        )
    }

    @Composable
    @Preview(showBackground = true)
    fun SignUpScreenPreview() {
        SignUpScreen()
    }


    override fun setUpUi() {

    }

    private fun getListenerOnDataSet(): DatePickerDialog.OnDateSetListener {
        return this
    }


    private fun showDialogMessage(kindOfMessage: Int, messageBody: String) {
        val alert = AlertDialogs(kindOfMessage, messageBody)
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

    private fun isValidPassword(): Boolean {
        val passwordLength = password.value.trim().length
        return passwordLength > MINIMAL_CHARACTERS_PASSWORD
    }

    private fun getEmail(): String = email.value

    private fun isValidNumber(): Boolean = phone.value.trim().length == PHONE_NUMBER_CHARACTERS

    private fun isTheEmailValidEmail(email: String): Boolean {
        return isValidEmail(email)
    }

    private fun areEmptyFields(): Boolean {
        val nombreIsEmpty = name.value.trim().isEmpty()
        val telefonoIsEmpty = phone.value.trim().isEmpty()
        val correoIsEmpty = email.value.trim().isEmpty()
        val passwordIsEmpty = password.value.trim().isEmpty()
        val birthDayIsEmpty = birthDay.value.trim().isEmpty()
        return nombreIsEmpty or telefonoIsEmpty or correoIsEmpty or passwordIsEmpty or birthDayIsEmpty
    }

    private fun resetErrorsInputs() {
        errorPhone.value = false
        errorEmail.value = false
        errorPassword.value = false
    }

    private fun validateForm(): Boolean {
        resetErrorsInputs()
        val passwordText = password.value.trim()
        val email = email.value.trim()
        val phone = phone.value.trim()
        var isValidPhone = true
        var isValidEmail = true
        var isValidPassword = true
        var areEmptyFields = false
        if (!isValidNumber()) {
            if (phone.isNotEmpty()) {
                errorPhone.value = true
            }
            isValidPhone = false
        }
        if (areEmptyFields()) areEmptyFields = true
        if (!isValidPassword()) {
            if (passwordText.isNotEmpty()) {
                errorPassword.value = true
            }
            isValidPassword = false
        }
        if (!isTheEmailValidEmail(getEmail())) {
            if (email.isNotEmpty()) {
                errorEmail.value = true
            }
            isValidEmail = false
        }
        return isValidPhone && isValidEmail && isValidPassword && !areEmptyFields
    }

    private fun doOnTextChange() {
        enableButton.value = validateForm()
        //changeColorTextButton()
    }

    private fun changeColorTextButton() {
        if (validateForm()) {
//            binding.buttonRegistarse.setTextColor(android.graphics.Color.WHITE)
        }
    }

    private fun getUser(): User {
        val nombre = name.value.trim()
        val telefono = phone.value.trim()
        val correo = email.value.trim()
        val password = password.value.trim()
        val birthDay = birthDay.value.trim()
        return User(nombre, telefono, correo, password, birthDay)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
        birthDay.value = selectedDate
        doOnTextChange()
    }

}