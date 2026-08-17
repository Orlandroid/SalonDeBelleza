package com.example.profile.historial_detail

import dagger.assisted.AssistedFactory


@AssistedFactory
interface HistoryDetailViewModelFactory {
    fun create(appointmentId: String): HistoryDetailViewModel
}