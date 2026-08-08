package com.example.citassalon.presentacion.features.info.products.categories

import com.example.domain.CategorySource
import dagger.assisted.AssistedFactory


@AssistedFactory
interface CategoriesViewModelFactory {
    fun create(source: CategorySource): CategoriesViewModel
}