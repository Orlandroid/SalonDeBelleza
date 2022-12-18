package com.example.citassalon.domain.state

sealed class SessionStatus() {
    object LOADING : SessionStatus()
    object NETWORKERROR : SessionStatus()
    object SUCESS : SessionStatus()
    object ERROR : SessionStatus()
}
