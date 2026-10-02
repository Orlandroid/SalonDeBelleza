package com.example.info.products.detalleproducto

import com.example.model.state.models.ProductSource
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductDetailViewModelFactory {
    fun create(
        source: ProductSource,
        productId: Int,
    ): DetailProductViewModel
}
