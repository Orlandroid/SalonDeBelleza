package com.example.citassalon.presentacion.features.app_navigation

import androidx.lifecycle.ViewModel
import com.example.citassalon.presentacion.main.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    val isConnected: StateFlow<Boolean> = networkMonitor.isConnected

    init {
        networkMonitor.register()
    }

    fun checkConnection() {
        networkMonitor.forceCheck()
    }

    override fun onCleared() {
        super.onCleared()
        networkMonitor.unregister()
    }
}