package com.example.citassalon.util

sealed class SessionStatus() {
    object LOADING : SessionStatus()
    object NETWORKERROR : SessionStatus()
    object SUCESS : SessionStatus()
    object ERROR : SessionStatus()
}
