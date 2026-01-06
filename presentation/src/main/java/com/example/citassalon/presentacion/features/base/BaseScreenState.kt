package com.example.citassalon.presentacion.features.base

sealed class BaseScreenState<out T> {
    object OnLoading : BaseScreenState<Nothing>()
    data class OnContent<T>(val content: T) : BaseScreenState<T>()
    data class OnError(val error:  Throwable) : BaseScreenState<Nothing>()
}

fun <T> BaseScreenState<T>.getContentOrNull(): T? =
    when (this) {
        is BaseScreenState.OnContent -> content
        else -> null
    }