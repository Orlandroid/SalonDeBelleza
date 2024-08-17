package com.example.domain.state

sealed interface DataState<out T> {
    data object Loading : DataState<Nothing>
    class Success<T>(val data: T) : DataState<T>
    class Error(val message: String) : DataState<Nothing>
}

