package com.example.citassalon.presentacion.features.schedule_appointment.branches


sealed class BaseScreenState<T> {
    class Loading<T> : BaseScreenState<T>()
    class Success<T>(val data: T) : BaseScreenState<T>()
    class Error<T>(val exception: Exception) : BaseScreenState<T>()
    class ErrorNetwork<T> : BaseScreenState<T>()
}