package com.example.info.products.products

import com.example.domain.ProductSource
import dagger.assisted.AssistedFactory


@AssistedFactory
interface ProductsViewModelFactory {
    fun create(
        source: ProductSource,
        category: String? = null
    ): ProductsViewModel
}