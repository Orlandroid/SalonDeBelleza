package com.example.citassalon.presentacion.features.auth.sign_up

import com.example.citassalon.presentacion.features.extensions.dateFormat
import com.example.citassalon.presentacion.features.extensions.getCurrentDateTime
import com.example.citassalon.presentacion.features.extensions.toStringFormat

data class SignUpUiState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val birthday: String = getCurrentDateTime().toStringFormat(dateFormat),
    var showErrorPhone: Boolean = false,
    var showErrorEmail: Boolean = false,
    var showErrorPassword: Boolean = false,
    var isEnableButton: Boolean = false,
)
