package com.example.citassalon.presentacion.features.auth.sign_up

sealed class SingUpEvents {
    data object OnSignUpClick : SingUpEvents()
    data class OnNameChange(val name: String) : SingUpEvents()
    data class OnPhoneChange(val phone: String) : SingUpEvents()
    data class OnEmailChange(val email: String) : SingUpEvents()
    data class OnPasswordChange(val password: String) : SingUpEvents()
    data class OnBirthDayChange(val birthday: String) : SingUpEvents()
}

sealed class SideEffects {
    data object OnLoginSuccess : SideEffects()
}