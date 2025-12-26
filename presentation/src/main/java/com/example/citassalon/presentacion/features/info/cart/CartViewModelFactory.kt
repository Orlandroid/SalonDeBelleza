package com.example.citassalon.presentacion.features.info.cart

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CartViewModelFactory {
    fun create(cardId: Int): CartViewModel
}