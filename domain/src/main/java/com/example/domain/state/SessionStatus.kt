package com.example.domain.state

sealed class SessionStatus {

    data object LOADING : SessionStatus()
    data object NETWORKERROR : SessionStatus()
    data object SUCCESS : SessionStatus()
    data object ERROR : SessionStatus()
    data object IDLE:SessionStatus()
}
