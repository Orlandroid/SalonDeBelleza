package com.example.citassalon.util

sealed class LoginStatus() {
    object LOADING : LoginStatus()
    object NETWORKERROR : LoginStatus()
    object SUCESS : LoginStatus()
    object ERROR : LoginStatus()
}
