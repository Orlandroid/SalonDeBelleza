package com.example.info.products.products

import com.example.model.state.models.ProductSource
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductsViewModelFactory {
    fun create(
        source: ProductSource,
        category: String? = null,
    ): ProductsViewModel
}
