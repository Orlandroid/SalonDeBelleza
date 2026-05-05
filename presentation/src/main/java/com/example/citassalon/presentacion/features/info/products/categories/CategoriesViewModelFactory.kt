package com.example.citassalon.presentacion.features.info.products.categories

import dagger.assisted.AssistedFactory


@AssistedFactory
interface CategoriesViewModelFactory {
    fun create(store: KindOfStore): CategoriesViewModel
}