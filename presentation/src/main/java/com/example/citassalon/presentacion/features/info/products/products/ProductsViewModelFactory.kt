package com.example.citassalon.presentacion.features.info.products.products

import com.example.data.remote.products.commons.product.ProductSource
import dagger.assisted.AssistedFactory


@AssistedFactory
interface ProductsViewModelFactory {
    fun create(
        source: ProductSource,
        category: String? = null
    ): ProductsViewModel
}