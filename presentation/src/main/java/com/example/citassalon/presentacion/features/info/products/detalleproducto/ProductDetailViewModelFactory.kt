package com.example.citassalon.presentacion.features.info.products.detalleproducto

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductDetailViewModelFactory {
    fun create(productId: Int): DetailProductViewModel
}