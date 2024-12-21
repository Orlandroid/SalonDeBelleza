package com.example.citassalon.presentacion.features.base


sealed class BaseScreenState<T> {
    class Idle<T> : BaseScreenState<T>()
    class Loading<T> : BaseScreenState<T>()
    class Success<T>(val data: T) : BaseScreenState<T>()
    class Error<T>(val exception: Exception? = null) : BaseScreenState<T>()
    class ErrorNetwork<T> : BaseScreenState<T>()
}