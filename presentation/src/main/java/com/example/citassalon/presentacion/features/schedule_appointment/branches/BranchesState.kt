package com.example.citassalon.presentacion.features.schedule_appointment.branches


sealed class BranchState<T> {
    class Loading<T> : BranchState<T>()
    class Success<T>(val data: T) : BranchState<T>()
    class Error<T>(val exception: Exception) : BranchState<T>()
    class ErrorNetwork<T> : BranchState<T>()
}