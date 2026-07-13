package com.example.citassalon.presentacion.features.info.products.categories

import com.example.data.remote.products.commons.product.ProductSource
import dagger.assisted.AssistedFactory


@AssistedFactory
interface CategoriesViewModelFactory {
    fun create(source: ProductSource): CategoriesViewModel
}