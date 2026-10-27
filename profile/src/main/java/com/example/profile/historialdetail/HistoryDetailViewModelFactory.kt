package com.example.profile.historialdetail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface HistoryDetailViewModelFactory {
    fun create(appointmentId: String): HistoryDetailViewModel
}
