package com.example.citassalon.presentacion.features.auth.login


sealed class LoginEvents {
    data class OnUserNameChange(val name: String) : LoginEvents()
    data class OnPasswordChange(val password: String) : LoginEvents()
    data class OnRememberUserChecker(val isCheck: Boolean) : LoginEvents()
    data class OnShowPassword(val show: Boolean) : LoginEvents()
    data object OnLoginClick : LoginEvents()
    data object OnForgetPasswordClick : LoginEvents()
    data object OnCloseDialogForgetPassword : LoginEvents()
    data class OnResetPassword(val email: String) : LoginEvents()
    data object GoToSignUpScreen : LoginEvents()
    data object OnSignUpWithGoogle : LoginEvents()
    data object OnSuccessLogin : LoginEvents()
}