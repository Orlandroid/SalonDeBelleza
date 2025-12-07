package com.example.citassalon.presentacion.features.base

sealed class BaseScreenStateV2<out T> {
    object OnLoading : BaseScreenStateV2<Nothing>()
    data class OnContent<T>(val content: T) : BaseScreenStateV2<T>()
    data class OnError(val error:  Throwable) : BaseScreenStateV2<Nothing>()
}

fun <T> BaseScreenStateV2<T>.getContentOrNull(): T? =
    when (this) {
        is BaseScreenStateV2.OnContent -> content
        else -> null
    }