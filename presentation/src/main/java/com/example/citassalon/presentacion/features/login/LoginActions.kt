package com.example.citassalon.presentacion.features.login

sealed class LoginActions {
    data class Login(val email: String, val password: String) : LoginActions()
    data object SignUp : LoginActions()
    data object SignUpWithGoogle : LoginActions()
    data object ForgetPassword : LoginActions()
    data class RememberUser(val remember: Boolean) : LoginActions()
}