package com.example.citassalon.presentacion.features.profile.historial_detail

import dagger.assisted.AssistedFactory


@AssistedFactory
interface HistoryDetailViewModelFactory {
    fun create(appointmentId: String): HistoryDetailViewModel
}