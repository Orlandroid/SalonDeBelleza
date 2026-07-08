package com.example.citassalon.presentacion.features.info.products.products

import com.example.data.remote.products.commons.ProductSource
import dagger.assisted.AssistedFactory


@AssistedFactory
interface ProductsViewModelFactory {
    fun create(source: ProductSource): ProductsViewModel
}