package com.example.citassalon.presentacion.features.info.products.detalleproducto

import com.example.data.remote.products.commons.product.ProductSource
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductDetailViewModelFactory {
    fun create(source: ProductSource, productId: Int): DetailProductViewModel
}