package com.example.citassalon.presentacion.features.info.products.products

import dagger.assisted.AssistedFactory


@AssistedFactory
interface ProductsViewModelFactory {
    fun create(category: String): ProductsViewModel
}